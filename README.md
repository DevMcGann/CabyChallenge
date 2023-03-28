# CabyChallenge
Challenge for Cabify

MVVM / Clean Architecture 
Retrofit for API calls
Room for internal data handling
Jetpack navigation 
Dagger Hilt 

I could add another version fully in Jetpack Compose if you are interested too. 

How it work? 

Well,  as the endpoint is only returning very basic data,  and i needed to load more data, as special offers and so.. I created an 
extension function to add this data
manually into the data class.   This is not optimal,  the BE should give all the information needed to handle offers. 

Then,  created a class called CalculatePrice(),  in which i handle the different prices for special offers and so... But again, 
this sould be handled by BE. 

What i would do on an optimal scenario,  could be ... Everytime the user taps on the "Go to cart" button,  i should send that product list 
(with quantities) to de BE, and then
shoud be the BE whos responsible for giving me back a response with all the offers applyed if there is any. 
Those offers are often changed on a dashboard,  and modify the data on the DBs.

About testing... Im no expert on this,  just had some experience with Mockito for Unit testing,  Espresso for UI testing and Papparazzi for UI testing
with Jetpack Compose (But used a very very custom implementation of my previous job).  
I tried to do testing on this project,  but i have some dependency problem with Mockito which i cant find any solution just yet.  (not even ChatGpt hehe) 


