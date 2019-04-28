import Auth from "@aws-amplify/auth";
import Analytics from "@aws-amplify/analytics";

import awsconfig from "./aws-exports";
import Amplify, { API, graphqlOperation } from "aws-amplify";
import * as queries from "./graphql/queries";
import * as mutations from "./graphql/mutations";

// retrieve temporary AWS credentials and sign requests
Auth.configure(awsconfig);
// send analytics events to Amazon Pinpoint
Analytics.configure(awsconfig);
Amplify.configure(awsconfig);
// const AnalyticsResult = document.getElementById("AnalyticsResult");
// const AnalyticsEventButton = document.getElementById("AnalyticsEventButton");
// let EventsSent = 0;
// AnalyticsEventButton.addEventListener("click", evt => {
//   Analytics.record("Amplify Tutorial Event").then(evt => {
//     const url =
//       "https://" +
//       awsconfig.aws_mobile_analytics_app_region +
//       ".console.aws.amazon.com/pinpoint/home/?region=" +
//       awsconfig.aws_mobile_analytics_app_region +
//       "#/apps/" +
//       awsconfig.aws_mobile_analytics_app_id +
//       "/analytics/events";
//     AnalyticsResult.innerHTML = "<p>Event Submitted.</p>";
//     AnalyticsResult.innerHTML += "<p>Events sent: " + ++EventsSent + "</p>";
//     AnalyticsResult.innerHTML +=
//       '<a href="' +
//       url +
//       '" target="_blank">View Events on the Amazon Pinpoint Console</a>';
//   });
// });

// Simple query
API.graphql(graphqlOperation(queries.listEvents)).then(res =>
  res.data.listEvents.items.map((item, i) => {
    console.log(item)
    document.querySelector("#tbody").innerHTML += `
      <tr>
        <td>${i+1}</td>
        <td>${item.title}</td>
        <td>${item.type}</td>
        <td>${item.description}</td>
        <td>${item.difficulty_level}</td>
      </tr>
    `
  })
);

function testfunc() {
  console.log("test")
}


var event = {
  title: "new-title-1",
  type: "new-type-1",
  location: "loca-1"
}

window.createEvent = function (event, resp) {
  API.graphql(graphqlOperation(mutations.createEvent, {
    input: event
  })).then(res => resp(res))
}

window.getAllEvents = function (resp) {
  API.graphql(graphqlOperation(queries.listEvents))
  .then(res => resp(res.data.listEvents.items))
} 

    // res.data.listEvents.items.map((item, i) => {
    //   document.querySelector("#tbody").innerHTML += `
    //     <tr>
    //       <td>${i+1}</td>
    //       <td>${item.title}</td>
    //       <td>${item.type}</td>
    //       <td>ipsum</td>
    //     </tr>
    //   `
    // })