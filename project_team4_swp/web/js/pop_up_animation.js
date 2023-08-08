// Xử lý Animation Pop-Up
function animationFade_Up(container, content)
{
    if (!content.className.includes("fade-Up"))
    {
        content.className += " fade-Up";
    }

    setTimeout(function () {
        if (content.className.includes("fade-Up"))
        {
            content.className = content.className.replace("fade-Up", "").trim();
        }
        container.style.display = "none";
    }, 600);
}

function animationFade_Down(contentE)
{
    if (!contentE.className.includes("fade_Down"))
    {
        contentE.className += " fade_Down";
    }
    setTimeout(function () {
        if (contentE.className.includes("fade_Down"))
        {
            contentE.className = contentE.className.replace("fade_Down", "").trim();
        }
    }, 600);
}


function animationZoomIn(container, content)
{
    if (!content.className.includes("zoom-In"))
    {
        content.className += " zoom-In";
    }

    setTimeout(function () {
        if (content.className.includes("zoom-In"))
        {
            content.className = content.className.replace("zoom-In", "").trim();
        }
        container.style.display = "none";
    }, 600);
}


function animationZoomOut(contentE)
{
    if (!contentE.className.includes("zoom-Out"))
    {
        contentE.className += " zoom-Out";
    }
    setTimeout(function () {
        if (contentE.className.includes("zoom-Out"))
        {
            contentE.className = contentE.className.replace("zoom-Out", "").trim();
        }
    }, 600);
}


function animationFade_Right(containerE)
{
    if (!containerE.className.includes("fade_left"))
    {
        containerE.className += " fade_left";
    }
    setTimeout(function () {
        if (containerE.className.includes("fade_left"))
        {
            containerE.className = containerE.className.replace("fade_left", "").trim();
        }
       containerE.style.display = 'none';
    }, 5000);


   

}