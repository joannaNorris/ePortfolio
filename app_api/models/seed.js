//Bring in the DB connection and the Trip schema
const mongoose = require('./db');
const Trip = mongoose.model('Trip');

//Read seed data from json file
var fs = require('fs');
var trips = JSON.parse(fs.readFileSync('data/trips.json', 'utf8'));

//Remove all existing documents and add the seed data   
const seedDB = async () => {
    await Trip.deleteMany({});
    await Trip.insertMany(trips);
};

//Close the DB connection when done and exit
seedDB().then(async () => {
    await mongoose.connection.close();
    console.log("DB seeded and connection closed");
    process.exit(0);
});
