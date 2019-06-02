
var webNarucivanje = angular.module('webNarucivanje', ['ngRoute', 'validation.match']);

webNarucivanje.config(function($routeProvider) {
	$routeProvider.when('/',{
		
		templateUrl: 'pocetna.html'
			
	}).when('/restorani',
	{
		templateUrl: 'restorani.html'
	}).when('/porudzbineDostavljac',
	{
		templateUrl: 'porudzbineDostavljac.html'
	}).when('/porudzbine',
	{
		templateUrl: 'porudzbine.html'
	}).when('/dostavljac',
	{
		templateUrl: 'dostavljacStrana.html'
	})
	.when('/vozila',
	{
		templateUrl: 'vozila.html'
	}).when('/artikli',
	{
		templateUrl: 'artikli.html'
	}).when('/korisnici',
	{
		templateUrl: 'karticaKorisnici.html'
	}).when('/mojNalog',{
		
		templateUrl: 'mojNalog.html'
			
	}).when('/kupac',{
		
		templateUrl: 'kupacStrana.html'
			
	}).when('/admin',{
		
		templateUrl: 'adminStrana.html'
			
	});			
});

webNarucivanje.config(function($logProvider){
    $logProvider.debugEnabled(true);
});