'use strict';
var gulp = require("gulp");
var gnf = require('gulp-npm-files');
var uglify = require("gulp-uglify");
var minifyCss = require("gulp-minify-css");
var util = require("gulp-util");
var del = require("del");
var name = util.env.name;
var src = "./src/main/webapp/assets";
var distSrc = "./src/main/webapp/dist";

// Copy dependencies to build/node_modules/
gulp.task('copyNpmDependenciesOnly', function() {
    gulp
        .src(gnf(null, './package.json'), {base:'./'})
        .pipe(gulp.dest(distSrc + "/js/"));
});

gulp.task("js", function () {
    return gulp.src([
        src + "/js/**/*.js"
    ])
        .pipe(uglify())
        .pipe(gulp.dest(distSrc + "/js/"))
})

gulp.task("css", function () {
    return gulp.src([src + "/css/**/*.css"])
        .pipe(minifyCss())
        .pipe(gulp.dest(distSrc + "/css/"))
});

gulp.task("font", function () {
    return gulp.src([src + "/font/*.*"])
        .pipe(gulp.dest(distSrc + "/font/"))
});

gulp.task("icon", function () {
    return gulp.src([src + "/favicon.ico"])
        .pipe(gulp.dest(distSrc))
});

gulp.task("clean", function () {          //清空所有文件
    return del([distSrc])
});

gulp.task("watchClean", function () {        //只清空JS，CSS文件即可,用于watch操作清空缓存
    return del([distSrc + "/js/*.js",
        distSrc + "/js/m/*.js",
        distSrc + "/js/portlet/*.js",
        distSrc + "/css/"]);
});

gulp.task("img", function () {
    return gulp.src([src + "/img/*.*"])
        .pipe(gulp.dest(distSrc + "/img/"))
});

gulp.task("default", gulp.series("clean",
    gulp.parallel("copyNpmDependenciesOnly", "js", "img", "icon", "font", "css"))
);

gulp.task("watch", function () {
    gulp.watch([
            src + "/js/**/*.js",
            src + "/css/**/*.css"],
        gulp.series("watchClean", gulp.parallel("js", "css"))
    )
});
