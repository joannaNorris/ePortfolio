const meals = (req, res) => {
    res.render('meals', {
        title: 'Meals',
        isMeals: true
    });
}

module.exports = {
    meals
};