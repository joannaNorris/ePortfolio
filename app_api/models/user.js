const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
    email: {
        type: String,
        required: true,
        unique: true
    },
    name: {
        type: String,
        required: true
    },
    hash: String,
    salt: String    
});

const crypto = require('crypto');
const jwt = require('jsonwebtoken');

//Method to set password for user during registration
userSchema.methods.setPassword = function(password) {
    this.salt = crypto.randomBytes(16).toString('hex');
    this.hash = crypto.pbkdf2Sync(password, this.salt, 1000, 64, 'sha512').toString('hex');
};

//Method to validate password during login
userSchema.methods.validatePassword = function(password) {
    var hash = crypto.pbkdf2Sync(password, this.salt, 1000, 64, 'sha512').toString('hex');
    return this.hash === hash;
}

//Method to generate JSON Web Token for user authentication
userSchema.methods.generateJWT = function() {
    return jwt.sign({
        //Payload for JSON Web Token
        _id: this._id,
        email: this.email,
        name: this.name
    }, process.env.JWT_SECRET, { //SECRET stored in .env file
        expiresIn: '1h' });//Token expires in 1 hour
}

const User = mongoose.model('users', userSchema);
module.exports = User;

mongoose.model('users', userSchema);

