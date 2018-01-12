# RESTVotingApp
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) <b>without frontend.</b>

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
<ul>
<li>Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)</li>
<li>Menu changes each day (admins do the updates)</li>
<li>Users can vote on which restaurant they want to have lunch at</li>
<li>Only one vote counted per user</li>
If user votes again the same day:
<li>If it is before 11:00 we assume that he changed his mind.</li>
<li>If it is after 11:00 then it is too late, vote can't be changed</li>
<li>Each restaurant provides new menu each day.</li>
</ul>
As a result, provide a link to github repository. It should contain the code, README.md with API documentation
<hr>
P.S.: Make sure everything works with latest version that is on github :)

P.P.S.: Asume that your API will be used by a frontend developer to build frontend on top of that.
<hr>

Since the assignment is in English, API is going to be in English too :)

<b><i>Admin commands:</i></b><p>
<b>1.	/voteapp/rest/admin/registernewuser</b><p>
HTTP method: POST<p>
Consumes: JSON User object<p>
Example:<p> 
{<p>
   “email”:”example@gmail.com”,<p>
    “password”:”example12345”,<p>
     “roles”:[“USER”]<p>
}<p>
Description: Creates a new user<p>

<b>2.	/voteapp/rest/admin/getuser</b><p>
HTTP method: GET<p>
Parameters: userId<p>
Description: Returns a user’s info got by ID<p>


<b>3.	/voteapp/rest/admin/allusers</b><p>
HTTP method: GET<p>
Description: Returns the info about all existing users<p>


<b>4.	/voteapp/rest/admin/deleteuser</b><p>
HTTP method: DELETE<p>
Parameters: userId<p>
Description: Removes a corresponding user.<p>


<b>5.	/voteapp/rest/admin/newrestaurant</b><p>
HTTP method: POST<p>
Consumes: JSON Restaurant object<p>
Example <p>
{<p>
    “restaurantName”:”newRestExample”<p>
}<p>
Description: Creates a new restaurant<p>


<b>6.	/voteapp/rest/admin/updaterestaurant</b><p>
HTTP method: PUT<p>
Consumes: JSON Restaurant object<p>
Example <p>
{<p>
      “id”: idOfRestToUpdate<p>
     “restaurantName”:”newRestName”<p>
} <p>
Parameters: restId<p>
Description: Updates restaurant’s name or other <p>
properties if required.<p>


<b>7.	/voteapp/rest/admin/deleterestaurant</b><p>
HTTP method: DELETE<p>
Parameters: restId<p>
Description: Removes restaurant with corresponding id<p>


<b>8.	/voteapp/rest/admin/newdish</b><p>
HTTP method: POST<p>
Consumes: JSON Dish object<p>
Example <p>
{<p>
    “dishName”:”someNewDishName”,<p>
     “price”: somePrice<p>
}<p>
Parameters: restId<p>
Description: Creates a new dish in corresponding<p>
restaurant provided by ID<p>


<b>9.	/voteapp/rest/admin/updatedish</b><p>
HTTP method: PUT<p>
Consumes: JSON Dish object<p>
Example <p>
{<p>
   “id”: dishIdToBeUpdated,<p>
   “dishName”:”someUpdatedDishName”,<p>
   “price”: someUpdatedPrice<p>
}<p>
Parameters: restId, dishId<p>
Description: Updates properties of corresponding<p>
dish in corresponding restaurant<p>


<b>10.	/voteapp/rest/admin/deletedish</b><p>
HTTP method: DELETE<p>
Parameters: restId, dishId<p>
Description: Removes corresponding <p>
dish from corresponding restaurant<p>


<b>11.	/voteapp/rest/admin/deletealldishes</b><p>
HTTP method: DELETE<p>
Parameters: restId<p>
Description: Removes all dishes from <p>
a corresponding restaurant<p>
<b>NOTE:</b> It’s better to use a SQL script<p>
every 24 hours, though.  Just in case<p>
SQL is messed up.<p>


<b>12.	/voteapp/rest/admin/restartvote</b><p>
HTTP method: POST<p>
Description: Sets all the counting <p>
properties (users’ and restaurants’)<p>
to 0.<p>
<b>NOTE:</b> This method, again, is used <p>
JUST in case something is wrong with SQL.<p>
<b><i> Authorized Users commands:</i></b><p>
<b>1.	/voteapp/rest/allrestaurants</b><p>
HTTP method: GET<p>
Description: Returns all the restaurants<p>


<b>2.	/voteapp/rest/getrestaurant</b><p>
HTTP method: GET<p>
Parameters: restId<p>
Description: Returns properties of<p>
a corresponding restaurant<p>

<b>3.	/voteapp/rest/alldishes</b><p>
HTTP method: GET<p>
Parameters: restId<p>
Description: Returns all the dishes<p>
of a corresponding restaurant<p>


<b>4.	/voteapp/rest/getdish</b><p>
HTTP method: GET<p>
Parameters: restId, dishId<p>
Description: Returns properties of<p>
a corresponding dish in a corresponding<p>
restaurant<p>

<b>5.	/voteapp/rest/updateuser</b><p>
HTTP method: PUT<p>
Consumes: JSON User Object<p>
Example<p> 
{<p>
   “email”: “newUpdatedEmail@gmail.com”,<p>
   “password”: “newUpdatedPassword”<p>
}<p>
Description: Updates email and password<p>
of a user who has been authorized.<p>
<b>NOTE:</b> Roles and voteCounter are immutable<p>
properties in this method, only email and password<p>
can be changed<p>


<b>6.	/voteapp/rest/voteforrest</b><p>
HTTP method: POST<p>
Parameters: restId<p>
Description: Get a vote from a user for a <p>
restaurant<p>


The passwords are stored in DB, encrypted with BCrypt. There’s the Basic authentication since that we have to use HTTPS here. 
Admin credentials - admin@gmail.com:admin12345
User credentials - mussalimov46@gmail.com:user12345
<hr>
Задание интересное, сомневаюсь, что такое дадут новичку.
