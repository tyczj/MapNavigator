MapNavigator
============

Easy to use library to get and display driving directions on Google Maps v2 in Android. This library gives you directions
and displays the route on the map.

Only works with Google Maps v2

Usage
=====

    GoogleMap map = getMap();
    Navigator nav = new Navigator(map,start,end);
    nav.findDirections(true, false);
    
Path displayed callbacks
========================

Sometimes you may need to know when the path is finished being displayed so you can show all the different route segments
in a listview or something.

Simple just implement the onPathSetListener

    nav.setOnPathSetListener(this);
    
make sure your fragment or activity implements the interface OnPathSetListener then implement onPathSetListenerand you will get 
the callback when the path is ready
