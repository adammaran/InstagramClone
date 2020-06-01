const moment = require('moment');
const _ = require("lodash");
const bcrypt = require("bcryptjs");
const { User, validateUser } = require("../models/user_model");
const { Post } = require("../models/post_model");

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

        return res.status(201).send({ token, user });
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.edit = async (req, res) => {
    try {
        const user = await User.findById(req.user._id);

        if (req.body.username) {
            if (await User.findOne({ username: req.body.username }))
                return res.status(400).send('Uneto korisničko ime već postoji.');
            else
                user.username = req.body.username;
        }

        if (req.body.email) {
            if (await User.findOne({ email: req.body.email }))
                return res.status(400).send('Uneta e-mail adresa već postoji.');
            else
                user.email = req.body.email;
        }

        if (req.body.fullName)
            user.fullName = req.body.fullName;

        if (req.body.bio)
            user.bio = req.body.bio;

        await user.save();

        return res.status(200).send(user);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.togglePrivate = async (req, res) => {
    try {
        const user = await User.findById(req.user._id);

        if (!user) {
            return res.status(404).send();
        }

        user.isPrivate = !user.isPrivate;

        await user.save();

        return res.status(200).send(user);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.toggleActive = async (req, res) => {
    try {
        const user = await User.findById(req.user._id);

        if (!user) {
            return res.status(404).send();
        }

        user.isActive = !user.isActive;

        await user.save();

        return res.status(200).send(user);
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

        if (!user)
            return res.status(404).send();

        return res.status(200).send(user);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send();
    }
};

exports.getCurrentUser = async (req, res) => {
    try {
        const user = await User.findById(req.user._id);

        if (!user)
            return res.status(404).send();

        return res.status(200).send(user);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send();
    }
};

exports.getUserStats = async (req, res) => {
    try {
        const user = req.params.id ? await User.findById(req.params.id) : await User.findById(req.user._id);
        const posts = await Post.find({ user_id: req.user._id });

        if (!user)
            return res.status(404).send();

        return res.status(200).send({
            posts: posts.length,
            followers: user.followers.length,
            following: user.following.length
        });
    } catch (ex) {
        console.log(ex);
        return res.status(500).send();
    }
};

exports.getFollowerList = async (req, res) => {
    try {
        const user = req.params.id ? await User.findById(req.params.id) : await User.findById(req.user._id);
        const followers = [];

        if (!user)
            return res.status(404).send();
        
        for (follower of user.followers) {
            followers.push(getFollowerInfo(follower));
        }

        return res.status(200).send(followers);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send();
    }
};

exports.getFollowingList = async (req, res) => {
    try {
        const user = req.params.id ? await User.findById(req.params.id) : await User.findById(req.user._id);
        const following = [];

        if (!user)
            return res.status(404).send();
        
        for (followee of user.following) {
            following.push(getFollowerInfo(followee));
        }

        return res.status(200).send(following);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send();
    }
};

const getFollowerInfo = async (id) => {
    try {
        const user = await User.findById(id);

        return { _id : user._id, username : user.username, fullName: user.fullName, avatar : user.avatar };
    } catch (ex) {
        console.log(ex);
    }
}