var map;
var clickedPos;
var allMarkers = [];

function initMap() {
    getCurrentLocation(pos => {
        map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: pos.coords.latitude, lng: pos.coords.longitude},
            zoom: 16,
        });

        plotEvents(map, eventsList)

        google.maps.event.addListener(map, 'click', function(event) {
            document.querySelector("#add-event-trigger").click()
            clickedPos = {
                lat: event.latLng.lat(),
                lng: event.latLng.lng()
            }
        });
    }) 
}

const getCurrentLocation = (position) => {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(position);
    }
}

const plotEvents = (map, events) => events.forEach(event => {
    var marker = plotMarker(map, event.location)
    marker.addListener("click", () => {
        getInfoWindow(event).open(map, marker)
    })
})

const plotMarker = (map, pos) => new google.maps.Marker({position: {
        lat: pos.lat, 
        lng: pos.lng
    }, 
    map: map
})

const saveEvent = () => {
    plotEvents(map, [{
        title: document.querySelector("#event-title").value,
        catagory: document.querySelector("#event-catagory").value,
        desc: document.querySelector("#event-desc").value,
        location: clickedPos,
    }])
    document.querySelector("#event-closer").click()
}

const getInfoWindow = (event) => new google.maps.InfoWindow({
    content: `
        <div>
            <h3>${event.title}</h3>
            <p>${event.desc}</p>
        </div>
    `
});

const eventsList = [
    {title: "title 1", desc: "desc 1", location: {lat:32.806671, lng:-86.791130}},
    {title: "title 2", desc: "desc 2", location: {lat:42.755966, lng:-107.302490}},
    {title: "title 3", desc: "desc 3", location: {lat:37.769337, lng:-78.169968}},
]



//GraphQL endpoint: https://5ohay2rnkba5lkg2ldyzgskwiy.appsync-api.ap-south-1.amazonaws.com/graphql
//GraphQL API KEY: da2-237y33cpofh2xcbhfnvue4dyne