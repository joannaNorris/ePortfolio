const contact = (req, res) => {
    res.render('contact', {
        title: 'Contact Travlr Getaways',
        isContact: true
    });
};

const contactSubmit = (req, res) => {
    console.log(req.body);

    res.render('contact', {
        title: 'Contact Travlr Getaways',
        message: 'Thank you for your message. We will get back to you shortly.'
    });
}

module.exports = {
    contact,
    contactSubmit
};