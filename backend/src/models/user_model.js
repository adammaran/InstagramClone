const Joi = require("@hapi/joi");
const mongoose = require("mongoose");
const jwt = require("jsonwebtoken");
const config = require("config");

const schema = new mongoose.Schema({
    fullName: {
        type: String,
        minlength: 2,
        maxlength: 100
    },
    username: {
        type: String,
        unique: true,
        required: true,
        minlength: 3,
        maxlength: 30
    },
    email: {
        type: String,
        unique: true,
        required: true,
        minlength: 4,
        maxlength: 100
    },
    password: {
        type: String,
        required: true,
        minlength: 8,
        maxlength: 1024
    },
    bio: {
        type: String,
        maxlength: 280
    },
    isActive: {
        type: Boolean,
        default: true
    },
    isPrivate: {
        type: Boolean,
        default: false
    },
    avatar: {
        type: Buffer
    },
    following: {
        type: [String]
    },
    followers: {
        type: [String]
    }
});

schema.methods.generateAccessToken = function () {
    const accessToken = jwt.sign({ _id: this._id }, config.get('jwtPrivateKey'));

    return accessToken;
};

const User = mongoose.model('User', schema);

const validateUser = (user) => {
    const schema = Joi.object({
        fullName: Joi.string().min(2).max(225),
        email: Joi.string().min(4).max(225).required().email(),
        password: Joi.string().regex(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/),
        username: Joi.string().min(3).max(60).required(),
        bio: Joi.string().max(500)
    });

    return schema.validate(user);
}

exports.User = User;
exports.validateUser = validateUser;