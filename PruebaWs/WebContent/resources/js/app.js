/**
 * App.js Angular modulo para configurar la interaccion
 * entre la vista y el controlador de los requisitos que seran implementados
 * @author Federico, Andres y Daniel
 */

const URL_HOME = "http://localhost:8080/PruebaWs/rest/";
const URL_USER_SERVICE = "ServicioUsuario/"
	

angular.module('app',[])

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

/**
 * Este controlador permite controlar la vista del login
 * y le agrega funcionalidad con el backend.
 */
.controller('loginCtrl', function($scope, loginService){
	$scope.id = '';
	$scope.password = '';
	$scope.captcha = '';
	
	$scope.autenticar = function() {
		loginService.autenticar($scope.id, $scope.password, $scope.captcha)
						.success(function(data, status, headers, config){
							console.log("Data recibida = " + data);
							//registro exitoso
							alert(data);
							console.log("Registro, status= "+status);
							//$location.url("/main_page);
						})
	};
	
})