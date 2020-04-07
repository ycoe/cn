package com.duoec.cn;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Random;

public class TraceLog {
    private static final List<Request> REQUEST_LIST = Lists.newArrayList();
    private static final List<Long> AGENT_IDS = Lists.newArrayList();
    private static final StringBuilder SB = new StringBuilder();

    @Test
    public void init() throws Exception {
        List<String> lines = Lists.newArrayList();
        Files.readLines(new File("/Users/xuwenzhen/Downloads/tt-20200330.log"), Charsets.UTF_8).forEach(line -> {
            lines.add(line);
        });
        Files.readLines(new File("/Users/xuwenzhen/Downloads/tt-20200331.log"), Charsets.UTF_8).forEach(line -> {
            lines.add(line);
        });

        List<StringBuilder> sbs = Lists.newArrayList();
        for (int i = 0; i < 31; i++) {
            sbs.add(new StringBuilder());
        }

        for (int i = 0; i < lines.size(); i++) {
            int index = i % sbs.size();
            String line = "2020-03-" + (index > 8 ? String.valueOf(index + 1) : "0" + (index + 1)) + lines.get(i).substring(10);
            sbs.get(index).append(line).append("\n");
        }
        for (int i = 0; i < sbs.size(); i++) {
            FileUtils.writeStringToFile(new File("/Users/xuwenzhen/Downloads/tt_202003" + (i > 8 ? "" : "0") +  (i + 1) + ".log"), sbs.get(i).toString(), Charsets.UTF_8);
        }
    }

    private Request process(String line) {
        int index = 0;
        int lastIndex = 0;

        Request request = new Request();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == ' ') {
                if (index == 0) {
                    request.time = line.substring(lastIndex, i);
                    setTime(request);
                } else if (index == 5) {
                    lastIndex += 6;
                    break;
                }

                lastIndex = i;
                index += 1;
            }
        }

        index = line.indexOf(" ", lastIndex);
        if (index == -1) {
            return null;
        } else {
            request.url = line.substring(lastIndex, index);
            lastIndex = index;
        }
        index = line.indexOf("\"", lastIndex);
        lastIndex = index + 3;
        index = line.indexOf("\"", lastIndex);
        request.referer = line.substring(lastIndex, index);


        lastIndex = index + 3;
        index = line.indexOf("\"", lastIndex);
        request.ua = line.substring(lastIndex, index);
        lastIndex = index;

        //开始处理userId
        request.userId = AGENT_IDS.get(REQUEST_LIST.size());

        setUa(request);
        return request;
    }

    private void setUa(Request request) {
        if ("-".equals(request.ua)) {
            request.ua = "";
            return;
        }
        int index = request.ua.indexOf("UserId/");
        if (index != -1) {
            request.ua = request.ua.substring(0, index) + "UserId/" + request.userId;
        }

        Random random = new Random();
        //处理fa
        request.fa = "FA1.0.1587" + random(random) + ".9" + random(random);

    }

    private int random(Random random) {
        return 100000000 + random.nextInt(999999999);
    }

    private void setTime(Request request) {
        if (request.time == null) {
            return;
        }
        request.time = request.time.replace("T", " ").replace("+08:00", ".0");
        request.hour = Integer.parseInt(request.time.substring(11, 13));
    }

    class Request {
        String time;
        String url;
        String ua;
        String referer;
        Long userId;
        String fa;
        String ip;
        Integer hour;

        @Override
        public String toString() {
            return time + "||||||||10|" + (userId == null ? "" : userId) + "|||||" + (ip == null ? "" : ip) + "||||||PV|多多网商运营||||||||PV||" + referer + "|" + url + "||||||||||||||房市头条|" + (ua == null ? "" : ua) + "|||" + (fa == null ? "" : fa) + "||||||||2|||||" + (userId == null ? "" : userId);
        }
    }
}
