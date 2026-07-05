/* GET homepage */
const index = (req, res) => {
  res.render('index', { 
    title: 'Travlr Getaways',
    isHome: true
  });
};

module.exports = {
 index                                           
};    