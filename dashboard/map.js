var map;
var clickedPos;
var allMarkers = [];

function initMap() {
    getCurrentLocation(pos => {
        map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: pos.coords.latitude, lng: pos.coords.longitude},
            zoom: 16,
        });

        plotMarker(
            map, 
            {lat: pos.coords.latitude, lng: pos.coords.longitude},
            {
                url: "https://i.imgur.com/N2gJ2KF.png",
                scaledSize: new google.maps.Size(50, 50),
            }
        )

        getAllEvents(data => {
            data.map(event => {
                var latlng = event.location.split(",")
                event.location = {
                    lat: Number(latlng[0]),
                    lng: Number(latlng[1])
                }
                plotEvents(map, [event])
            })
        })

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
    var marker = plotMarker(
        map, 
        event.location, 
        event.is_finished?{
            url: "http://maps.google.com/mapfiles/ms/icons/green-dot.png",
            scaledSize: new google.maps.Size(48, 48),
        }:""
    )
    marker.addListener("click", () => {
        getInfoWindow(event).open(map, marker)
    })
})

const plotMarker = (map, pos, icon) => new google.maps.Marker({
    position: {lat: pos.lat, lng: pos.lng}, 
    map: map,
    icon: icon
})

const saveEvent = () => {
    var event = {
        title: document.querySelector("#event-title").value,
        type: document.querySelector("#event-catagory").value,
        description: document.querySelector("#event-desc").value,
        location: `${clickedPos.lat},${clickedPos.lng}`,
        is_finished: false,
        difficulty_level: 2,
        organizer_photo: "...",
        finisher_photo: "...",
        finished_by: "..."
    }
    createEvent(event, (res) => {
        console.log(res)
        event.location = clickedPos
        plotEvents(map, [event])
        document.querySelector("#event-closer").click()
        updateTable()
    })
}

const getInfoWindow = (event) => new google.maps.InfoWindow({
    content: `
        <div>
            <h3>${event.title}</h3>
            <p>${event.description}</p>
        </div>
    `
});

const updateTable = () => {
    document.querySelector("#tbody").innerHTML = ""
    getAllEvents(data => {
        console.log(data)
        data.map((event, i) => {
            document.querySelector("#tbody").innerHTML += `
            <tr>
                <td>${i+1}</td> 
                <td>${event.title}</td>
                <td>${event.type}</td>
                <td>${event.description}</td>
                <td>${event.difficulty_level}</td>
            </tr>
        `
        })
    })
}


//GraphQL endpoint: https://5ohay2rnkba5lkg2ldyzgskwiy.appsync-api.ap-south-1.amazonaws.com/graphql
//GraphQL API KEY: da2-237y33cpofh2xcbhfnvue4dyne