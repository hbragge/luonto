<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 	<xsl:output method="html"/>
	<xsl:template match="/">
		
				<xsl:apply-templates select="/rss/channel"/>
			
	</xsl:template>
	
	<xsl:template match="/rss/channel">
		<div class="rss-sisalto">
		
			<ul class="lista">
				<xsl:apply-templates select="item"/>
			</ul>
			
		</div>
	</xsl:template>
	<xsl:template match="/rss/channel/item">
		<li class="itemlista">
			
			
			<a href="{link}" title="{description}">
				<xsl:value-of select="title"/>
			</a>
			
			
			<div class="itemikuvaus">
				<xsl:value-of select="description"/>
			</div>
			
			
		</li>
	</xsl:template>

</xsl:stylesheet>