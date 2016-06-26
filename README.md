## rdf-neo
Importer RDF2Neo4j

## Run
Change application.properties if needed, and then Run RdfToNeoImporter's main.<br/>
The files are assumed to be in Turtle format.<br/>
The script assumes a running Neo4j server on port 7474 (the host is specified via properties).

## Import
RDF properties having literals as objects are imported as node properties, while properties linking URIs are imported as relations.<br/>

Any URI that is imported as a property key or as a relation is saved in its abbreviated form (curie: <a hef="http://www.w3.org/TR/curie"> http://www.w3.org/TR/curie</a>) by using the namespaces present in the files. The import will break when trying to save a URI as property key or relation without a prior curie transformation.
