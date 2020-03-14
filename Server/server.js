const express = require('express');
const bodyParser = require('body-parser');
const port = 3000
const {Pool, Client} = require("pg");
const app = express();
const https = require('https');

const pool = new Pool({
	user	: "postgres",
	host	: "rentalcentral.c7gdjiu5zeuo.us-east-1.rds.amazonaws.com",
	database: "nodescraper",
	password: "student123",
	port	: "5432"
});

let queryString = `SELECT * FROM listings`

app.use(bodyParser.json())

app.use(
	bodyParser.urlencoded({
		extended: true,
	})
)

app.get('/', (req, res) =>{
	 res.json({ info: 'Node.js, Express, and Postgres API' })
	
});



const getListings = (req, response) => {
	pool.query(queryString, (err, results) => {
		if (err){
			throw err
		}

		response.status(200).json(results.rows)
	})
}

app.get('/listings', getListings)

app.listen(port, () => {
	console.log(`App running on port ${port}.`)
})


