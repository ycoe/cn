<div class="pagination-row clearfix m-bottom-md">
    <div class="pull-left vertical-middle hidden-xs">${total} 条记录</div>
    <div class="pull-right pull-left-sm">
        <div class="inline-block vertical-middle m-right-xs">Page ${pageNo} of ${totalPage} </div>
        <ul class="pagination vertical-middle">
        <#if showFirst || hasPre>
            <li><a href="${urls['1']}"><i class="fa fa-step-backward"></i></a></li>
        <#else >
            <li class="disabled"><a href="#"><i class="fa fa-step-backward"></i></a></li>
        </#if>
        <#if hasPre>
            <li><a href="${urls[(pageNo - 1)?string]}"><i class="fa fa-caret-left large"></i></a></li>
        <#else >
            <li class="disabled"><a href="#"><i class="fa fa-caret-left large"></i></a></li>
        </#if>
        <#if hasNext>
            <li><a href="${urls[(pageNo + 1)?string]}"><i class="fa fa-caret-right large"></i></a></li>
        <#else >
            <li class="disabled"><a href="#"><i class="fa fa-caret-right large"></i></a></li>
        </#if>
        <#if showLast || hasNext>
            <li><a href="${urls[totalPage?string]}"><i class="fa fa-step-forward"></i></a></li>
        <#else >
            <li class="disabled"><a href="#"><i class="fa fa-step-forward"></i></a></li>
        </#if>
        </ul>
    </div>
</div>