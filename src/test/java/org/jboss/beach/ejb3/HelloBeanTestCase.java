/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.beach.ejb3;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.Date;
import java.util.Properties;

import javax.ejb.EJBContainer;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;
import org.junit.Test;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class HelloBeanTestCase
{
   private static final Logger log = Logger.getLogger(HelloBeanTestCase.class);
   
   private static String getBaseURLToResource(String resource)
   {
      URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
      String s = url.toString();
      return s.substring(0, s.length() - resource.length());
   }
   
   @Test
   public void test1() throws NamingException
   {
      log.info("Test #1");
      
      Properties properties = new Properties();
      // Not according to spec!
      properties.setProperty(EJBContainer.EMBEDDABLE_MODULES_PROPERTY, getBaseURLToResource("org/jboss/beach/ejb3/HelloBean.class"));
      EJBContainer container = EJBContainer.createEJBContainer(properties);
      
      // Note that global naming isn't working yet.
      InitialContext ctx = new InitialContext();
      Hello bean = (Hello) ctx.lookup("HelloBean/local");
      
      String now = new Date().toString();
      String actual = bean.sayHello(now);
      
      assertEquals("Hello " + now, actual);
      
      container.close();
   }
}
