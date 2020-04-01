const moment = require('moment');
const _ = require("lodash");
const bcrypt = require("bcryptjs");
const { User, validateUser } = require("../models/user_model");

exports.create = async (req, res) => {
    const { error } = validateUser(req.body);
    if (error) {
        return res.status(400).send(error);
    }

    if (await User.findOne({ username: req.body.username })) {
        return res.status(400).send('Uneto korisničko ime već postoji.');
    }

    if (await User.findOne({ email: req.body.email })) {
        return res.status(400).send('Uneta e-mail adresa već postoji.');
    }

    user = new User(_.pick(req.body, ['username', 'name', 'lastName', 'email', 'password']));
    const salt = await bcrypt.genSalt(10);
    user.password = await bcrypt.hash(user.password, salt);
    user.isActive = true;

    try {
        await user.save();

        console.log('Registration block - User registered: ' + user.email);

        return res.status(201).send(user);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.getUser = async (req, res) => {
    try {
        const user = User.findById(req.user._id).select('-__v -lessons -freeLessonTaken -password -active');

        if (!user) {
            return res.status(404).send();
        }

        res.send(user);
    } catch (ex) {
        res.status(500).send();
    }
}

exports.findbyId = async (req, res) => {
    try {
        const user = await User.findById(req.params.id);

        if (!user) {
            return res.status(404).send();
        }

        return res.send(user);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send();
    }
}