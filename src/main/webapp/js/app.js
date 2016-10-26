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
            $routeProvider.when('/addBody/:batchId/:stage/:size', {
                            templateUrl: 'partials/addBody.html',
                            controller: AddBodyController
                        });
            $routeProvider.when('/addSleeve/:batchId/:stage/:size', {
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
	})
	
   .constant('appConfig', {
     servicePath: 'http://localhost:8080/measurementapp/services/'
    });
	

function NewMeasurementController($scope,$routeParams, $location,$http,appConfig) {
    
    
    $scope.init = function (){
        $scope.getSizeList();
        $scope.batchId;
        
    }
    $scope.disable=true;
    $scope.newMeasurementEntry={}
    $scope.newMeasurementEntry.customer="Please enter a Batch Number";
    $scope.newMeasurementEntry.style="Please enter a Batch Number";

    $scope.checkBatchNo= function(key){
        
    $scope.newMeasurementEntry.customer="Invalid Batch Number";
    $scope.newMeasurementEntry.style="Invalid Batch Number";


            var params = {};
            params.id=key;

            $http({
                method: 'GET',
                url: appConfig.servicePath + 'BatchService/checkBatchNo',
                params: params
            }).then(
                    function success(response) {
                      console.log(response.data);
                      
                      if(response.data.count=="1"){
                        $scope.newMeasurementEntry.customer=response.data.customer;
                        $scope.newMeasurementEntry.style=response.data.styleName;
                        $scope.batchId=response.data.id;
                        $scope.batchNo=$scope.newMeasurementEntry.batchNo;
                      }
                      
                    },
                    function error(error) {

                        console.log(error);
                    }
            );

    }
    
    $scope.getSizeList = function (){
        console.log("done");
        var params = {};
        
        $http({
            method: 'GET',
            url: appConfig.servicePath + 'SizeService/getSizeList',
            params: params
        }).then(
                function success(response) {
                  //console.log(response.data);
                   $scope.sizes = response.data;
 			
                },
                function error(error) {
                    
                    console.log(error);
                }
        );
        
        
        
    }
     switch ($routeParams.stage){
        case 'beforePresetting':
            $scope.stage=1;
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
//	 $scope.sizes = [
//        {id: 1, s: 'XS'},
//        {id: 2, s: 'S'},
//        {id: 3, s: 'M'},
//        {id: 4, s: 'L'},
//        {id: 5, s: 'XL'}
//      ];

        $scope.save = function() {
		console.log("Saving : " + $scope.newMeasurementEntry)
	};
        
        
        $scope.init();
        
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
            $scope.stage='Before Presetting';
            break;
        case '2':
           $scope.batchNumber='12342';
           $scope.styleName ='ab111';
           $scope.stage='Before Presetting';

           break;
        case '3':
           $scope.batchNumber='12343';
           $scope.styleName ='ab112';
           $scope.stage='Before Presetting';
           break;
        case '4':
           $scope.batchNumber='12344';
           $scope.styleName = 'ab111';
           $scope.stage='Before Presetting';
           break;
        case '5':
           $scope.batchNumber='12345';
           $scope.styleName ='ab112';
           $scope.stage='After Presetting';
           break;

        default:
           $location.path('/batchList');

     }

     $scope.measurements = [


        {id: 1, measurementName: 'Chest Width',s1g1: 1, s1g2:2, s1g3:3, s1g4:0, s1g5:0,s2g1: 0, s2g2:0, s2g3:0, s2g4:0, s2g5:0,s3g1: 0, s3g2:0, s3g3:0, s3g4:0, s3g5:0},
        {id: 2, measurementName: 'Hem Width',s1g1: 0, s1g2:0, s1g3:0, s1g4:0, s1g5:0,s2g1: 0, s2g2:0, s2g3:0, s2g4:0, s2g5:0,s3g1: 0, s3g2:0, s3g3:0, s3g4:0, s3g5:0},
        {id: 3, measurementName: 'CB Length',s1g1: 0, s1g2:0, s1g3:0, s1g4:0, s1g5:0,s2g1: 0, s2g2:0, s2g3:0, s2g4:0, s2g5:0,s3g1: 0, s3g2:0, s3g3:0, s3g4:0, s3g5:0},
        {id: 4, measurementName: 'CF Length',s1g1: 0, s1g2:0, s1g3:0, s1g4:0, s1g5:0,s2g1: 0, s2g2:0, s2g3:0, s2g4:0, s2g5:0,s3g1: 0, s3g2:0, s3g3:0, s3g4:0, s3g5:0},
        {id: 5, measurementName: 'Sleeve Opening',s1g1: 0, s1g2:0, s1g3:0, s1g4:0, s1g5:0,s2g1: 0, s2g2:0, s2g3:0, s2g4:0, s2g5:0,s3g1: 0, s3g2:0, s3g3:0, s3g4:0, s3g5:0},
        {id: 6, measurementName: 'Sleeve Length',s1g1: 0, s1g2:0, s1g3:0, s1g4:0, s1g5:0,s2g1: 0, s2g2:0, s2g3:0, s2g4:0, s2g5:0,s3g1: 0, s3g2:0, s3g3:0, s3g4:0, s3g5:0},
        {id: 7, measurementName: 'Sleeve Width',s1g1: 0, s1g2:0, s1g3:0, s1g4:0, s1g5:0,s2g1: 0, s2g2:0, s2g3:0, s2g4:0, s2g5:0,s3g1: 0, s3g2:0, s3g3:0, s3g4:0, s3g5:0}
      ];


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



function AddBodyController($scope,$routeParams, $location,$http,appConfig){


    $scope.init = function (){
        $scope.entries = [
        {id: 1, gmt: 'Garment1'},
        {id: 2, gmt: 'Garment2'},
        {id: 3, gmt: 'Garment3'},
        {id: 4, gmt: 'Garment4'},
        {id: 5, gmt: 'Garment5'}
        ];
        $scope.getGarmentEntry();
        console.log("done");
    }
    
    $scope.getGarmentEntry = function (){
        var params={};
        params.bid=$routeParams.batchId;
        params.sizeid=$routeParams.size;
        params.stageid=$routeParams.stage;
        
                     $http({
                        method: 'GET',
                        url: appConfig.servicePath + 'GarmentEntryService/getGarmentEntry',
                        params: params
                    }).then(
                            function success(response) {
                                    console.log(response.data);
                                    $scope.entries =response.data;
                        
                                       
                                    for (var i=0;i<5;i++){
                                        if(response.data.length<i+1){
                                            $scope.entries[i]={}
                                            
                                        }
                                        $scope.entries[i].gmt='Garment ' +(i+1);
                      
                                }
                                   // console.log($scope.entries);
                            },
                            function error(error) {

                                console.log(error);
                            }
                    );
            
        
    }

     

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
       
//        for (var x=0;x<$scope.entries.length;x++){
//           // console.log($scope.cgLength);
//            
//                     var params={};
//                     params.bid=$routeParams.batchId;
//                     params.sid=$routeParams.size;
//                     params.stageid=$routeParams.stage;
//                     params.cblength=$scope.entries[x].cbLength;
//                     params.cflength=$scope.entries[x].cfLength;
//                     params.chestwidth=$scope.entries[x].chestWidth;
//                     params.hemwidth=$scope.entries[x].hemWidth;
//                     console.log(params);
//
//                    $http({
//                        method: 'POST',
//                        url: appConfig.servicePath + 'GarmentEntryService/createGarmentEntry',
//                        params: params
//                    }).then(
//                            function success(response) {
//
//                            },
//                            function error(error) {
//
//                                console.log(error);
//                            }
//                    );
//            
//        }

       };
       
       $scope.init();
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
function BatchListCtrl($scope, $routeParams, $location, $http,appConfig){
    
    $scope.init = function (){
        console.log("sgfdj");
        $scope.getBatchList();
    }
    
    $scope.getBatchList = function (){
        var params={};
        
        $http({
            method: 'GET',
            url: appConfig.servicePath + 'BatchService/getBatchList',
            params: params
        }).then(
                function success(response) {
                  $scope.batches=response.data;
 			
                },
                function error(error) {
                    
                    console.log(error);
                }
        );
    }
//     $scope.batches = [
//        {id: 1, batchNo: '12341',styleName: 'ab111',stage: '1'},
//        {id: 2, batchNo: '12342',styleName: 'ab112',stage: '2'},
//        {id: 3, batchNo: '12343',styleName: 'ab113',stage: '2'},
//        {id: 4, batchNo: '12344',styleName: 'ab112',stage: '3'},
//        {id: 5, batchNo: '12345',styleName: 'ab112',stage: '1'}
//
//      ];
     // console.log($scope.batches);
      
      $scope.init();
      };

var services = angular.module('exampleApp.services', ['ngResource']);

services.factory('BatchListService', function($resource) {

	return $resource('rest/new/:id', {id: '@id'});
});
