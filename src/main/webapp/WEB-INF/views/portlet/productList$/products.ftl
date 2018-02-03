<#if products??>
<ul id="products">
    <#list products as product>
    <li>
        <div class="desc">
            <p>
            ${product.summary!}
            </p>
        </div>
        <div class="img-holder">
            <img src="${product.coverImage?thumb(196, 196)}" alt="${product.name}"/>
        </div>
        <h6><a href="/product/${product.id}.html">${product.name}</a></h6>
    </li>
    </#list>
</ul>
</#if>