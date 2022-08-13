<%!
/****************************************************************************
**
** Function:  setUpInputSource()
** Input:   fileName - String
** Output:  is - InputSource
**
** Description:  This function returns the input source.
**
***************************************************************************/

private org.xml.sax.InputSource setUpInputSource(String fileName)
{
   org.xml.sax.InputSource is = new org.xml.sax.InputSource();
   is = setupFileSource(fileName);
   return is;
} 

/****************************************************************************
**
** Function:  setUpFileSource()
** Input:   fileName - String
** Output:  is - InputSource
**
** Description:  This function returns the input source.
**
***************************************************************************/
private org.xml.sax.InputSource setupFileSource(String filename)
{
   try
   {
      java.io.File xmlFile = new java.io.File( filename );
      if ( xmlFile.isFile() )
      {
         java.io.FileReader fr = new java.io.FileReader( xmlFile );
         System.out.println(":::::::::: "+fr.getEncoding());
         org.xml.sax.InputSource is = new org.xml.sax.InputSource(fr);
         return is;
      }
      else
      {
      }    
   }
   catch ( NullPointerException  npe )
   {
      System.out.println( "Null pointer exception" + npe); 
   }
   catch ( SecurityException se )
   {
      System.out.println( "Security Exception" + se); 
   }
   catch ( java.io.FileNotFoundException fnfe )
   {
      System.out.println("File Not Found Exception" + fnfe);
   }
   return new org.xml.sax.InputSource();
}
%>
