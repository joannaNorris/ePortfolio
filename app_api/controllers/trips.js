const mongoose = require('mongoose');
const Trip = require('../models/travlr'); //register model
const Model = mongoose.model('Trip');

//GET all trips
const tripsList = async (req, res) => {
    try {
        console.log('Fetching all trips');
        const trips = await Trip
        .find();
        res.status(200).json(trips);
    } catch (error) {
        res.status(500).json({message: "Trips not found"});
    }

};

//GET trip by code
const tripsFindByCode = async (req, res) => {
    try {
        console.log('Fetching trip by code:', req.params.code);
        const trip = await Trip.findOne({'code': req.params.code}); //returns single record
        console.log('Found trip:', trip);
        if (!trip) {
            return res.status(404).json({
                message: "Trip not found"
            });
        }

        res.status(200).json(trip);

    } catch (error) {
        res.status(500).json({
            message: "Server error"
        });
    } 

};

//POST add new trip
const tripsAddTrip = async (req, res) => {
    const newTrip = new Trip({
        code: req.body.code,
        name: req.body.name,
        length: req.body.length,
        start: req.body.start,
        resort: req.body.resort,
        perPerson: req.body.perPerson,
        image: req.body.image,
        description: req.body.description
    })

    const q = await newTrip.save();
    
    if (!q) {
        return res.status(400).json({
            message: "Trip not added"
        });
    } else {
        return res.status(201).json(q);
    }
    
};

// PUT: /trips/:tripCode - Adds a new Trip
// Regardless of outcome, response must include HTTP status code
// and JSON message to the requesting client
const tripsUpdateTrip = async(req, res) => {
    try {
        // Uncomment for debugging
        console.log(req.params);
        console.log(req.body);

        const q = await Trip
            .findOneAndUpdate(
                { code : req.params.code },
                {
                    code: req.body.code,
                    name: req.body.name,
                    length: req.body.length,
                    start: req.body.start,
                    resort: req.body.resort,
                    perPerson: req.body.perPerson,
                    image: req.body.image,
                    description: req.body.description
                },
                { returnDocument: 'after' } //return the updated document
            );
          //  .exec();

            if(!q) { // Database returned no data
                return res
                    .status(404).json({
                        message: "Trip not updated"
                    });
            }
            // Return resulting updated trip
            return res.status(200).json(q);

        } catch (error) {
        return res.status(500).json({
            message: error.message
        });

    }

        // Uncomment the following line to show results of operation
        // on the console
        // console.log(q);
};

module.exports = {
    tripsList,
    tripsFindByCode,
    tripsUpdateTrip,
    tripsAddTrip
};