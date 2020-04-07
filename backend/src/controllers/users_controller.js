const moment = require('moment');
const _ = require("lodash");
const bcrypt = require("bcryptjs");
const { User, validateUser } = require("../models/user_model");

exports.create = async (req, res) => {
    try {
        const { error } = validateUser(req.body);

        if (error)
            return res.status(400).send(error);

        if (await User.findOne({ username: req.body.username }))
            return res.status(400).send('Uneto korisničko ime već postoji.');

        if (await User.findOne({ email: req.body.email }))
            return res.status(400).send('Uneta e-mail adresa već postoji.');

        user = new User(req.body);
        const salt = await bcrypt.genSalt(10);

        user.password = await bcrypt.hash(user.password, salt);

        await user.save();

        console.log('Registration block - User registered: ' + user.email);

        const token = {
            token: user.generateAccessToken()
        };

        return res.status(201).send(token);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.uploadAvatar = async (req, res) => {
    try {
        const user = await User.findById(req.user._id);

        if (!user) {
            return res.status(404).send();
        }

        user.avatar = req.file.buffer;

        await user.save();

        return res.status(201).send(user);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.follow = async (req, res) => {
    try {
        const user1 = await User.findById(req.user._id);
        const user2 = await User.findById(req.params.id);
        const alreadyFollowing = false;

        user1.following.forEach(user => {
            if (user === req.params.id)
                alreadyFollowing = true;
        });

        if (alreadyFollowing)
            return res.status(400).send('Already following');

        user1.following.push(user2._id);
        user2.followers.push(user1._id);

        await user1.save();
        await user2.save();

        return res.status(200).send();
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.unfollow = async (req, res) => {
    try {
        const user1 = await User.findById(req.user._id);
        const user2 = await User.findById(req.params.id);

        const followingIndex = user1.following.findIndex(user => user === req.params.id);
        const followerIndex = user2.followers.findIndex(user => user === req.user._id);

        if (followingIndex === -1 || followerIndex === -1)
            return res.status(400).send('Not following the requested user');

        user1.following.splice(followingIndex, 1);
        user2.followers.splice(followerIndex, 1);

        await user1.save();
        await user2.save();

        return res.status(200).send();
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.findbyUsername = async (req, res) => {
    try {
        const user = await User.find({ username: { "$regex": new RegExp(req.params.username.replace(/\s+/g, "\\s+"), "gi") } })

        if (!user) {
            return res.status(404).send();
        }

        return res.status(200).send(user);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send();
    }
};