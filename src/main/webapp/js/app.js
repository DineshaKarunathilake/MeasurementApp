angular.module('exampleApp', ['ngRoute', 'ngCookies', 'exampleApp.services','xeditable'])
	.config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {
			
			$routeProvider.when('/create', {
                            templateUrl: 'partials/create.html',
                            controller: CreateController
			});
			$routeProvider.when('/selectStage', {
            				templateUrl: 'partials/selectStage.html',
            				controller: SelectStageController
            			});

			$routeProvider.when('/addNewMeasurement/:stage', {
                            templateUrl: 'partials/addNewMeasurement.html',
                            controller: NewMeasurementController
                        });
            $routeProvider.when('/addBody/:stage/:size', {
                            templateUrl: 'partials/addBody.html',
                            controller: AddBodyController
                        });
            $routeProvider.when('/addSleeve/:stage/:size', {
                            templateUrl: 'partials/addSleeve.html',
                            controller: AddSleeveController
                        });

            $routeProvider.when('/batchList',
                         {
                             templateUrl: 'partials/batchList.html',
                             controller: BatchListCtrl

                         });
            $routeProvider.when('/viewBatch/:batch',
                         {
                             templateUrl: 'partials/viewBatch.html',
                             controller: ViewBatchCtrl

                         });

			$routeProvider.otherwise(
            			{
                            templateUrl: 'partials/firstpage.html',
                        	controller: FirstPageController
                        });


			$locationProvider.hashPrefix('!');
			
			/* Register error provider that shows message on failed requests or redirects to login page on
			 * unauthenticated requests */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
			        return {
			        	'responseError': function(rejection) {
			        		var status = rejection.status;
			        		var config = rejection.config;
			        		var method = config.method;
			        		var url = config.url;
			      
			        		if (status == 401) {
			        			$location.path( "/firstpage" );
			        		} else {
			        			$rootScope.error = method + " on " + url + " failed with status " + status;
			        		}
			              
			        		return $q.reject(rejection);
			        	}
			        };
			    }
		    );
		    
		    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
		     * as soon as there is an authenticated user */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
		        return {
		        	'request': function(config) {
		        		var isRestCall = config.url.indexOf('rest') == 0;

		        		return config || $q.when(config);
		        	}
		        };
		    }
	    );
		   
		} ]
		
	)
	.run(function($rootScope, $location, $cookieStore,	editableOptions) {

	     editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
		
		/* Reset error when a new view is loaded */
		$rootScope.$on('$viewContentLoaded', function() {
			delete $rootScope.error;
		});


		 /* Try getting valid user from cookie or go to login page */
		var originalPath = $location.path();


		$rootScope.initialized = true;
	});

function NewMeasurementController($scope,$routeParams, $location) {
    $scope.disable=true;
    $scope.newMeasurementEntry={}
    $scope.newMeasurementEntry.customer="Please enter a Batch Number";
    $scope.newMeasurementEntry.style="Please enter a Batch Number";

    $scope.checkBatchNo= function(){
    $scope.newMeasurementEntry.customer="Invalid Batch Number";
    $scope.newMeasurementEntry.style="Invalid Batch Number";

         if($scope.newMeasurementEntry.batchNo=="12341"){
             $scope.newMeasurementEntry.customer="YOUR Customer";
             $scope.newMeasurementEntry.style="Your style";
             $scope.batchNo=$scope.newMeasurementEntry.batchNo;
         }

    }
     switch ($routeParams.stage){
        case 'beforePresetting':
            $scope.stage=0;
            $scope.stageName='Before Presetting';
            break;
        case 'afterPresetting':
           $scope.stage=2;
           $scope.stageName='After Presetting';
           break;
        case 'afterDyeing':
           $scope.stage=3;
           $scope.stageName='After Dyeing';
           break;
        default:
           $location.path('/selectStage');

     }
	 $scope.sizes = [
        {id: 1, s: 'XS'},
        {id: 2, s: 'S'},
        {id: 3, s: 'M'},
        {id: 4, s: 'L'},
        {id: 5, s: 'XL'}
      ];

        $scope.save = function() {
		console.log("Saving : " + $scope.newMeasurementEntry)
	};
};


function FirstPageController($scope, $routeParams, $location) {
}


function SelectStageController($scope, $rootScope) {
}


function ViewBatchCtrl($scope,$routeParams, $location) {

    console.log($routeParams.batch)
  switch ($routeParams.batch){
        case '1':
            $scope.batchNumber='12341';
            $scope.styleName ='ab112';
            $scope.stage='0';
            break;
        case '2':
           $scope.batchNumber='12342';
           $scope.styleName ='ab111';
           $scope.stage='2';

           break;
        case '3':
           $scope.batchNumber='12343';
           $scope.styleName ='ab112';
           $scope.stage='1';
           break;
        case '4':
           $scope.batchNumber='12344';
           $scope.styleName = 'ab111';
           $scope.stage='0';
           break;
        case '5':
           $scope.batchNumber='12345';
           $scope.styleName ='ab112';
           $scope.stage='1';
           break;

        default:
           $location.path('/batchList');

     }

     console.log("Batch Number"+$scope.batchNumber+ "Style" + $scope.styleName)

$scope.id = $routeParams.id;

};


function CreateController($scope, $location, BlogPostService) {

	$scope.blogPost = new BlogPostService();
	
	$scope.save = function() {
		$scope.blogPost.$save(function () {
			$location.path('/');
		});
	};
};



function AddBodyController($scope,$routeParams, $location){


     $scope.entries = [
        {id: 1, gmt: 'Garment1',chestWidth: 0, hemWidth:0,cbLength:0,cfLength:0},
        {id: 2, gmt: 'Garment2'},
        {id: 3, gmt: 'Garment3'},
        {id: 4, gmt: 'Garment4'},
        {id: 5, gmt: 'Garment5'}
      ];

      switch ($routeParams.size){
             case '1':

                 $scope.size='XS';
                 break;
             case '2':

                $scope.size='S';
                break;
             case '3':

                $scope.size='M';
             case '4':

                $scope.size='L';
                break;
             case '5':

                $scope.size='XL';
                break;
             default:
                $location.path('/addNewMeasurement/:stage');

          }

  // save edits
      $scope.saveTable = function() {
      console.log($scope.entries);

       };

       }

function AddSleeveController($scope,$routeParams, $location){


 $scope.entries = [
        {id: 1, gmt: 'Sleeve1'},
        {id: 2, gmt: 'Sleeve2'},
        {id: 3, gmt: 'Sleeve3'},
        {id: 4, gmt: 'Sleeve4'},
        {id: 5, gmt: 'Sleeve5'}
      ];


      switch ($routeParams.size){
             case '1':

                 $scope.size='XS';
                 break;
             case '2':

                $scope.size='S';
                break;
             case '3':

                $scope.size='M';
             case '4':

                $scope.size='L';
                break;
             case '5':

                $scope.size='XL';
                break;
             default:
                $location.path('/addNewMeasurement/:stage');

          }

  // save edits
     $scope.saveTable = function() {
        console.log($scope.entries);

        };

}
function BatchListCtrl($scope, $routeParams, $location){
     $scope.batches = [
        {id: 1, batchNo: '12341',styleName: 'ab111',stage: '1'},
        {id: 2, batchNo: '12342',styleName: 'ab112',stage: '2'},
        {id: 3, batchNo: '12343',styleName: 'ab113',stage: '2'},
        {id: 4, batchNo: '12344',styleName: 'ab112',stage: '3'},
        {id: 5, batchNo: '12345',styleName: 'ab112',stage: '1'}

      ];
      console.log($scope.batches);
      };

var services = angular.module('exampleApp.services', ['ngResource']);

services.factory('BatchListService', function($resource) {

	return $resource('rest/new/:id', {id: '@id'});
});
