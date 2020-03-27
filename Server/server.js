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

let ottawa =	`SELECT * FROM ottawa`
let toronto = 	`SELECT * FROM toronto`
let montreal = 	`SELECT * FROM montreal`

app.use(bodyParser.json())

app.use(
	bodyParser.urlencoded({
		extended: true,
	})
)

app.get('/', (req, res) =>{
	 res.json({ info: 'Node.js, Express, and Postgres API' })
	
});

const getOttawa = (req, response) => {
	pool.query(ottawa, (err, results) => {
		if (err){
			throw err
		}

		response.status(200).json(results.rows)
	})
}

const getMontreal = (req, response) => {
        pool.query(montreal, (err, results) => {
                if (err){
                        throw err
                }

                response.status(200).json(results.rows)
        })
}

const getToronto = (req, response) => {
        pool.query(toronto, (err, results) => {
                if (err){
                        throw err
                }

                response.status(200).json(results.rows)
        })
}


app.get('/ottawa', getOttawa)
app.get('/toronto', getToronto)
app.get('/montreal', getMontreal)


app.listen(port, () => {
	console.log(`App running on port ${port}.`)
})


