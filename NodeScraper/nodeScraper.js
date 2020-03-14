const kijiji = require("kijiji-scraper");
const fs = require('fs');
const { Pool, Client } = require("pg");

const pool = new Pool({
	user	: "postgres",
	host 	: "rentalcentral.c7gdjiu5zeuo.us-east-1.rds.amazonaws.com",
	database: "nodescraper",
	password: "student123",
	port	: "5432"
});

let options = {
    minResults: 400 //Minimum number of results (20 per page, so 40 = 2 pages)
};

let params = {
    locationId: 1700185,    //Location for ottawa area.
    categoryId: 30349001,   //Category for rentals
    keywords: "Student",    //Search student rentals
    adType: "OFFER"         //Show only people offering rentals
};

function replaceAll(str, find, replace){
	return str.replace(new RegExp(find, 'g'), replace);
}



//Scrape using the options and parameters objects, then send the ads that were scraped to be parseds
kijiji.search(params, options).then(function(ads) {
    //Array of all listings
    let listings = [];
    //Loop through the ads array
    for (let i = 0; i < ads.length; ++i) {
	let url = "\'" + ads[i].url + "\'";
	let badTitle = ads[i].title;
	let badDescription = ads[i].description;

	let goodTitle = replaceAll(badTitle, "\'", " ")
	let goodDescription = replaceAll(badDescription, "\'", " ")
	

	let gooderTitle = "\'" + goodTitle + "\'"
	let gooderDescription = "\'" + goodDescription + "\'"
	
	    let queryString =`INSERT INTO listings (url, title, description, images) 
				VALUES
				(${url}, ${gooderTitle}, ${gooderDescription}, \'{${ads[i].images}}\')`;

	pool.query(queryString, (err, res)=>{
			if(err && err.code != 23505){
				console.log("---------------------------------------------------------------------------------------------------------");
				console.log(gooderTitle);
				console.log(ads[i].url);
				console.log(gooderDescription);
				console.log(err);
				console.log("---------------------------------------------------------------------------------------------------------");
				}
			});
	    
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
