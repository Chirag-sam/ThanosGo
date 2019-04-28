var map;
function initMap() {
    getCurrentLocation(pos => {
        map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: pos.coords.latitude, lng: pos.coords.longitude},
            zoom: 16,
        });

        new google.maps.Marker({position: {
            lat: pos.coords.latitude,
            lng: pos.coords.longitude
        }, map: map});
    }) 
}

const getCurrentLocation = (position) => {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(position);
    }
}
