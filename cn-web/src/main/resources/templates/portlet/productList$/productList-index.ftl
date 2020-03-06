<#if products??>
<h3>
    PRODUCTS<br>
    产品展示
</h3>
<ul>
    <#list products as product>
    <li>
        <h6><a href="/product/${product.id}.html">${product.name}</a></h6>
        <div class="desc">
            <p>
            ${product.summary!}
            </p>
        </div>
        <div class="img-holder">
            <img src="${product.coverImage?thumb(294, 263)}" alt="${product.name}"/>
        </div>
    </li>
    </#list>
</ul>
</#if>