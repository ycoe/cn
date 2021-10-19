<h3 class="m-b-30">
    ABOUT US<br>
    关于我们
</h3>
<div class="about-us-content">
    ${article.content!}
    <a class="more" href="/article/list-10.html">${'more'?i18n} &gt;</a>
</div>
<img style="display: inline-block;height:370px" src="${article.coverImage!?thumb(474, 256)!}" alt="${article.title!}">