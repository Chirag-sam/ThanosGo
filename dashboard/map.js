var map;

function initMap() {
    getCurrentLocation(pos => {
        map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: pos.coords.latitude, lng: pos.coords.longitude},
            zoom: 2,
        });

        plotEvents(map, eventsList)

        google.maps.event.addListener(map, 'click', function(event) {
            console.log(event.latLng.lat())
            console.log(event.latLng.lng())
        });
    }) 
}

const getCurrentLocation = (position) => {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(position);
    }
}

/*
    event: {
        name,
        catagory,
        desc,
        location
    }
*/
const plotEvents = (map, events) => events.forEach(event => plotMarker(map, event.location))

const plotMarker = (map, pos) => new google.maps.Marker({position: {
        lat: pos.lat, 
        lng: pos.lng
    }, 
    map: map
});

const eventsList = [
    {location: {lat:32.806671, lng:-86.791130}},
    {location: {lat:42.755966, lng:-107.302490}},
    {location: {lat:37.769337, lng:-78.169968}},
]

