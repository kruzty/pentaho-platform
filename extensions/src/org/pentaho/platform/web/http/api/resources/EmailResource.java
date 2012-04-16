/*
 * This program is free software; you can redistribute it and/or modify it under the 
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software 
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this 
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html 
 * or from the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright 2008 Pentaho Corporation.  All rights reserved.
 *
 * Created Mar 03, 2012
 * @author Ezequiel Cuellar
 */

package org.pentaho.platform.web.http.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.platform.plugin.services.email.EmailConfiguration;
import org.pentaho.platform.plugin.services.email.EmailService;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.WILDCARD;

@Path("/emailconfig/")
public class EmailResource extends AbstractJaxRSResource {

  /**
   * The logger for this class
   */
  private static final Log logger = LogFactory.getLog(EmailResource.class);

  private EmailService emailService = null;

  /**
   * Constructs an instance of this class using the default email service
   *
   * @throws IllegalArgumentException Indicates that the default location for the email configuration file is invalid
   */
  public EmailResource() throws IllegalArgumentException {
    this(new EmailService());
  }

  /**
   * Constructs an instance of this class using the default email service
   *
   * @throws IllegalArgumentException Indicates that the default location for the email configuration file is invalid
   */
  public EmailResource(final EmailService emailService) throws IllegalArgumentException {
    if (emailService == null) {
      throw new IllegalArgumentException();
    }
    this.emailService = emailService;
  }

  @PUT
  @Path("/setEmailConfig")
  @Consumes({WILDCARD})
  public Response setEmailConfig(@QueryParam("configuration") EmailConfiguration emailConfiguration) {
    try {
      emailService.setEmailConfig(emailConfiguration);
    } catch (Exception e) {
      return Response.serverError().build();
    }
    return Response.ok().build();
  }

  @GET
  @Path("/getEmailConfig")
  @Produces({ APPLICATION_JSON, APPLICATION_XML })
  public EmailConfiguration getEmailConfig() {
    try {
      return emailService.getEmailConfig();
    } catch (Exception e) {
      return new EmailConfiguration();
    }
  }

  @GET
  @Path("/sendEmailTest")
  @Consumes({WILDCARD})
  @Produces({MediaType.TEXT_PLAIN})
  public Response sendEmailTest(@QueryParam("configuration") EmailConfiguration emailConfiguration)
      throws Exception {
    emailService.sendEmailTest(emailConfiguration);
    return Response.ok().build();
  }
}
