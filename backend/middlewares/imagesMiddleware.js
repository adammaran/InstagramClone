const multer = require("multer");

module.exports.upload = multer({
    limits: {
        fileSize: 1024 * 1024 * 10
    },
    fileFilter(req, file, cb) {
        if (!file.originalname.match(/\.(jpg|jpeg|png)$/)) {
            return cb(new Error('Please upload an image'));
        }

        cb(undefined, true);
    }
});
