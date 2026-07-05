var express = require('express');
var router = express.Router();
const controller = require('../controllers/contact');

router.get('/', controller.contact);
router.post('/', controller.contactSubmit);

module.exports = router;