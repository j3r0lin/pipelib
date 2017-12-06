static String toCamelCase( String text, boolean capitalized = false ) {
  text = text.replaceAll( "(_)([A-Za-z0-9])", { Object[] it -> it[2].toUpperCase() } )
  return capitalized ? capitalize(text) : text
}
 
static String toSnakeCase( String text ) {
  text.replaceAll( /([A-Z])/, /_$1/ ).toLowerCase().replaceAll( /^_/, '' )
}

static String toImageName( String text ) {
  toSnakeCase(text).replaceAll('_', '-')
} 
