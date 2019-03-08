## Create AWS ElasticSearch snapshot

1. create the IAM roles from https://docs.aws.amazon.com/elasticsearch-service/latest/developerguide/es-managedomains-snapshots.html#es-managedomains-snapshot-create

2. use node.js to send the AWS signed request to register the snapshot, remember to export the credentails.
```javascript
var AWS = require('aws-sdk');

var region = 'us-west-2';
var domain = 'search-fdt-elasticsearch-domain-be6isgtin2xyplxyv7lcjt4xga.us-west-2.es.amazonaws.com'

var type = '_snapshot';
var repo = 'fdt-snapshot-repo';

var json = {
  "type": "s3",
  "settings": {
    "bucket": "fdt-elasticsearch-snapshot",
    "region": "use-west-2",
    "role_arn": "arn:aws:iam::279043971974:role/fdt-elasticsearch-snapshot-role"
  }
};


indexDocument(json);

function indexDocument(document) {
  var endpoint = new AWS.Endpoint(domain);
  var request = new AWS.HttpRequest(endpoint, region);

  request.method = 'PUT';
  request.path += type + '/' + repo;

  request.body = JSON.stringify(document);
  request.headers['host'] = domain;
  request.headers['Content-Type'] = 'application/json';
  // Content-Length is only needed for DELETE requests that include a request
  // body, but including it for all requests doesn't seem to hurt anything.
  // request.headers["Content-Length"] = request.body.length;

  var credentials = new AWS.EnvironmentCredentials('AWS');
  var signer = new AWS.Signers.V4(request, 'es');
  signer.addAuthorization(credentials, new Date());

  var client = new AWS.HttpClient();
  client.handleRequest(request, null, function (response) {
    console.log(response.statusCode + ' ' + response.statusMessage);
    var responseBody = '';
    response.on('data', function (chunk) {
      responseBody += chunk;
    });
    response.on('end', function (chunk) {
      console.log('Response body: ' + responseBody);
    });
  }, function (error) {
    console.log('Error: ' + error);
  });
}
```

3. run `curl -XPUT https://search-fdt-elasticsearch-domain-be6isgtin2xyplxyv7lcjt4xga.us-west-2.es.amazonaws.com/_snapshot/fdt-snapshot-repo/fdt-inv-snapshot` to manually take a snapshot
