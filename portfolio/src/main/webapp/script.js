// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['I am 18 years old', 'My favorite color is purple.', 'I play the violin', 'I love desserts.'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}
function openTab(evt, cityName) {
  // Declare all variables
  var i, tabcontent, tablinks;

  // Get all elements with class="tabcontent" and hide them
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }

  // Get all elements with class="tablinks" and remove the class "active"
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }

  // Show the current tab, and add an "active" class to the link that opened the tab
  document.getElementById(cityName).style.display = "block";
  evt.currentTarget.className += " active";
}

//Get the element with id="defaultOpen" to open by default
document.getElementById("defaultOpen").click();


function getComment() {
const commentLimit = window.location.search;
console.log(commentLimit);
const urlParams = new URLSearchParams(commentLimit);
var maxstring = urlParams.get('comment-limit')
console.log(maxstring);
if (maxstring==null){maxstring=10} 
fetch('/data?comment-limit='+maxstring).then(response => response.json()).then((comments) => {
    const commentId= document.getElementById("comments-container");
    commentId.innerHTML='';
    comments.forEach((comment) => {
       /* let button = document.createElement("button");
        button.classList.add("button");
        button.innerHTML="Delete";
        button.addEventListener("click",deleteComment);*/
        commentId.appendChild(createListElement(comment.comment));
        }
    )})
}   


function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

/** Tells the server to delete the comment. */
function deleteComment() {
  fetch('/delete-data', {method: 'POST'}).then(() => {console.log("we got here, delete.")})
  getComment();
}

var map
function createMap() {
   map = new google.maps.Map(
      document.getElementById('map'),
      {center: {lat: 37.422, lng: -122.084}, zoom: 16});
 
      console.log("map is running");
}

