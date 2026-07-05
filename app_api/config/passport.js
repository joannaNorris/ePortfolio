const passport = require('passport');
const LocalStrategy = require('passport-local').Strategy;
const Users = require('../models/user');
const mongoose = require('mongoose');
const User = mongoose.model('users');

//Configure Passport to use Local Strategy for user authentication
passport.use(new LocalStrategy({
    usernameField: "email", //Use email as the username field
    },
    async (email, password, done) => {
        const q = await User.findOne({ email: email }).exec();
            if (!q) {
                return done(null, false, { message: 'Incorrect username.' });
            }
            if (!q.validatePassword(password)) {
                return done(null, false, { message: 'Incorrect password.' });
            }
            return done(null, q);
    }
));