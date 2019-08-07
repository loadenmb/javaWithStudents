<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/TR/xhtml1/strict">
<xsl:output method="html" encoding="UTF-8" indent="yes"/>
    <xsl:template match="/">
        <html>
        		<head>
        			<title>carXMLserver Webseite</title>
     				<link rel="stylesheet" href="public/style.css" />
        			<style>        			
		     			<xsl:choose>
							<xsl:when test="carXML/css_backgroundcolor1">
							  body {
								    background-color: <xsl:value-of select="carXML/css_backgroundcolor1"/>;  				
		        				}
						    </xsl:when>					   
						</xsl:choose>
					  h1, h2, h3, h4, h5, h6 {					  
					  	  <xsl:choose>
					    	 <xsl:when test="carXML/css_fontheader">
						 		 font-family: <xsl:value-of select="carXML/css_fontheader"/>;
						  	</xsl:when>					   
						  </xsl:choose>						  
						  <xsl:choose>
					    	 <xsl:when test="carXML/css_fontsizeheader">
						 		 font-size: <xsl:value-of select="carXML/css_fontsizeheader"/>;
						  	</xsl:when>					   
						  </xsl:choose> 				
		        		}		        	
		        		#wrapper {	
		        			<xsl:choose>
		        				<xsl:when test="carXML/css_backgroundcolor2">
						 		 	background-color: <xsl:value-of select="carXML/css_backgroundcolor2"/>;
						  		</xsl:when>					   
						  </xsl:choose>	        	
		        			<xsl:choose>
					    	 <xsl:when test="carXML/css_fontcontent">
						 		 font-family: <xsl:value-of select="carXML/css_fontcontent"/>;
						  	</xsl:when>					   
						  </xsl:choose> 						  
						  <xsl:choose>
					    	 <xsl:when test="carXML/css_fontsizecontent">
						 		 font-size: <xsl:value-of select="carXML/css_fontsizecontent"/>;
						  	</xsl:when>					   
						  </xsl:choose> 
						}       			
        			</style>
				</head>
            <body>
            	 <div id="wrapper">
	                <h1>carXMLserver</h1>      
						 <xsl:choose>
					     <xsl:when test="carXML">
					        <h2>Autos von Benutzer ID: <xsl:value-of select="carXML/id"/></h2>
					     </xsl:when>
					     <xsl:otherwise>
					       <h2>Benuter ID eingeben um Autos zu sehen</h2>
					       <form method="get" name="carXML" type="urlencoded">
					       	<input type="text" name="u" value="" />
					       	<input type="submit" />
					       </form>
					     </xsl:otherwise>
					   </xsl:choose>	               
	                <ul>
	              	 <xsl:for-each select="carXML/car">
	                    <li>
	                    	   <p>Titel: <xsl:value-of select="name"/></p>
	                    		<p>ID: <xsl:value-of select="id"/></p>	                
	                        <p>PS: <xsl:value-of select="ps"/></p>	                  
	                        <p>Kilometerstand: <xsl:value-of select="mileage"/></p>
	                        <p>Zulassung: <xsl:value-of select="admission"/></p>	
	                        <p>Mietpreis: <xsl:value-of select="rental_price"/></p>	
	                        <p>Standort: <xsl:value-of select="location"/></p>	                     
	                        <p>Austattung: <xsl:value-of select="fixture"/></p>	              
	                        <p>Info:</p>
	                        <p><xsl:value-of select="text"/></p>
	                   </li>
	                 </xsl:for-each>
	                </ul>
					 <div id="footer"></div>	             
	           </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>