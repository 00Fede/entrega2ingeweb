/**
 * App.js Angular modulo para configurar la interaccion entre la vista y el
 * controlador de los requisitos que seran implementados
 * 
 * @author Federico, Andres y Daniel
 */

const URL_HOME = "http://localhost:8080/PruebaWs/rest/";
const URL_USER_SERVICE = "ServicioUsuario/"
const URL_RESERVA_SERVICE = "ServicioReserva/"
	

angular.module('app',['ngRoute','ngCookies'])

/**
 * Servicio que se encarga de ejecutar el servicio web de login
 */
.service('loginService', function($http){
	this.autenticar = function(id, pass, captcha){
		return $http({
			method: "POST",
			url: URL_HOME+URL_USER_SERVICE+"login",
			params:{
				id:id,
				pass:pass,
				captcha:captcha
			}
			
		})
	}
})
.config(['$routeProvider', function($routeProvider) {
	// Cuando este en '/' corre la configuracion del json
	// las rutas se hacen relativas a donde esta el index.html
	$routeProvider.when('/', {
		templateUrl: "pages/login.html",
		controller: "loginCtrl"
			});
	$routeProvider.when('/main',{
		templateUrl: "pages/main.html",
		controller: "mainCtrl"
	})
	
	// aqui irian las otras rutas
}])
.service('solicitarReserva', function($http) {
	
	this.solicitarReserv = function(idInv, idDev, fecha, tiempo) {
		return $http({
			method: "POST",
			url: URL_HOME+URL_RESERVA_SERVICE+"crearReserva",
			params:{
				idInv: idInv,
				idDev: idDev,
				fecha: fecha,
				tiempo: tiempo
			}
		});
		
	}
	
	
})


/**
 * Este controlador permite controlar la vista del login y le agrega
 * funcionalidad con el backend.
 */
.controller('loginCtrl', function($cookies, $scope, $location, loginService){
	$scope.id = '';
	$scope.password = '';
	$scope.captcha = '';
	
	var cookieId = $cookies.get('sessionID');
	console.log("User id session cookie = " + cookieId);
	
	$scope.autenticar = function() {
		loginService.autenticar($scope.id, $scope.password, $scope.captcha)
						.success(function(data, status, headers, config){
							
							console.log("Data recibida = " + data);
							alert(data.message);
							console.log("Registro, status= "+status);
							if(data.estado==200){
								//solo si el registro es exitoso
								var today = new Date();
								var expired = new Date(today);
								console.log("cookie generated at " + expired);
								expired.setMinutes(today.getMinutes() + 30);
								//expired.setDate(today.getDate() + 1);
								console.log("cookies expires at " + expired);
								$cookies.put('sessionID',$scope.id,{expires: expired});
								$location.url("/main");
							}
						})
	};
	
})
.controller('mainCtrl',  function($scope, $cookies, $log, $location){
	$scope.idUsuario = $cookies.get('sessionID');
	$scope.$watch($scope.idUsuario, function(newValue) {
        console.log('Cookie changed, string: ' + $scope.idUsuario)
        if($scope.idUsuario===undefined){
        	$location.url('/'); //vuelve al login
        }
    });
	console.log("SessionID obtained from cookies in main = " + $scope.idUsuario);
	
	
})

.controller('solicitarReservaCtrl', function($scope, solicitarReserva){
	$scope.id = '';
	$scope.idDev = '';
	$scope.fecha = '';
	$scope.tiempo = '';
	
	$scope.solicitarReserv = function() {
		solicitarReserva.solicitarReserv($scope.id, $scope.idDev, $scope.fecha, $scope.tiempo)
						.success(function(data, status, headers, config){
							console.log("Data recibida = " + data);
							//Solicitud Exitosa
							alert(data);
							console.log("Registro, status= "+status);
							
						})
	};
	
})
