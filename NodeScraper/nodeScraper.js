const kijiji = require("kijiji-scraper");
const fs = require('fs');
const {
        Pool,
        Client
} = require("pg");

const pool = new Pool({
        user: "postgres",
        host: "rentalcentral.c7gdjiu5zeuo.us-east-1.rds.amazonaws.com",
        database: "nodescraper",
        password: "student123",
        port: "5432"
});

let options = {
        minResults: 400 //Minimum number of results (20 per page, so 40 = 2 pages)
};

let params = {
        locationId: 1700185, //Location for ottawa area.
        categoryId: 30349001, //Category for rentals
        keywords: "Student", //Search student rentals
        adType: "OFFER" //Show only people offering rentals
};

let locations = "ottawa";

function replaceAll(str, find, replace) {
        return str.replace(new RegExp(find, 'g'), replace);
}

function innerFunction(locations, ads){
	//Array of all listings
	let listings = [];
	console.log("Done " + locations);

	//Loop through the ads array
	for (let i = 0; i < ads.length; ++i) {

			let url = "\'" + ads[i].url + "\'";
			let title = ads[i].title;
			let description = ads[i].description;
			let addr = ads[i].attributes.location.mapAddress;
			let lat = ads[i].attributes.location.latitude;
			let lon = ads[i].attributes.location.longitude;
			let price = ads[i].attributes.price;

			title = replaceAll(title, "\'", " ");
			description = replaceAll(description, "\'", " ");
			addr = replaceAll(addr, "\'", " ");

			title = "\'" + title + "\'"
			description = "\'" + description + "\'"
			addr = "\'" + addr + "\'"

			let queryString = `INSERT INTO ${locations} (url, title, description, latitude, longitude, address, price, images)
							VALUES
							(${url}, ${title}, ${description}, ${lat}, ${lon}, ${addr}, ${price}, \'{${ads[i].images}}\')`;

			pool.query(queryString, (err, res) => {
					if (err && err.code != 23505) {
							console.log("---------------------------------------------------------------------------------------------------------");
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
}

//Third Call
function callbackFunctionTwo() {
     
		params = {
			locationId: 1700273,
			categoryId: 30349001,
			keywords: "Student",
			adType: "OFFER"
		};
		locations = 'toronto';
        kijiji.search(params, options).then(function(ads){
			innerFunction(locations, ads);
		}).catch(console.error);
    
}

//Second call
function callbackFunctionOne() {
	params = {
			locationId: 1700281,
			categoryId: 30349001,
			keywords: "étudiant",
			adType: "OFFER"
	};
	locations = 'montreal';
	kijiji.search(params, options).then(function(ads){
		innerFunction(locations, ads);
		callbackFunctionTwo();
	}).catch(console.error);
}

//First call
kijiji.search(params, options).then(function(ads){
	innerFunction(locations, ads);
	callbackFunctionOne();
}).catch(console.error);
