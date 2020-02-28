const kijiji = require("kijiji-scraper");
const fs = require('fs');

let options = {
    minResults: 40 //Minimum number of results (20 per page, so 40 = 2 pages)
};

let params = {
    locationId: 1700185,    //Location for ottawa area.
    categoryId: 30349001,   //Category for rentals
    keywords: "Student",    //Search student rentals
    adType: "OFFER"         //Show only people offering rentals
};

//Scrape using the options and parameters objects, then send the ads that were scraped to be parseds
kijiji.search(params, options).then(function(ads) {
    //Array of all listings
    let listings = [];
    //Loop through the ads array
    for (let i = 0; i < ads.length; ++i) {
        //Listing data structure used for each json object
        let listing = { 
            title: ads[i].title,
            description: ads[i].description, 
            images: ads[i].images,
            date: ads[i].date,
            attributes: ads[i].attributes,
            url: ads[i].url 
        };
        //Push the listing data to the array of all listings
        listings.push(listing);
    }
    //Parse structre to json and write to a file
    let listingsData = JSON.stringify(listings);
    fs.writeFileSync('kijiji-listings.json', listingsData);
}).catch(console.error);

//Search for ads
kijiji.search(params, options);