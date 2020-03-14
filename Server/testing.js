const url = "http://ec2-18-212-6-101.compute-1.amazonaws.com:3000/listings";
const fetch = require("node-fetch");
const options = {
	header: {
		Authorization: "Bearer 6Q************"
	}
};

fetch(url, options)
	.then(res => res.json())
	.then(data => console.log(data));
