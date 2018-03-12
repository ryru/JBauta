/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for BautaFactory
 *
 */
class BautaFactoryTest {

  private static final byte[] IPV4_MASK_1 = {
      (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0x00};
  private static final byte[] IPV6_MASK_1 = {
      (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
      (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
  private static Inet4Address ipv4Mask1;
  private static Inet6Address ipv6Mask1;

  static {
    try {
      ipv4Mask1 = (Inet4Address) InetAddress.getByAddress(IPV4_MASK_1);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  static {
    try {
      ipv6Mask1 = (Inet6Address) InetAddress.getByAddress(IPV6_MASK_1);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  private static BautaFactory factory;

  @BeforeAll
  public static void beforeAll() {
    factory = new BautaFactory();
  }

  @Test
  void createIPv4DfaultMask() {
    BautaV4 testObj = factory.createIPv4();
    assertTrue(testObj.getClass() == BautaV4.class);
  }

  @Test
  void createIPv6DefaultMask() {
    BautaV6 testObj = factory.createIPv6();
    assertTrue(testObj.getClass() == BautaV6.class);
  }

  @Test
  void testCreateIPv4NonDefaultMask() {
    BautaV4 testObj = factory.createIPv4(ipv4Mask1);
    assertTrue(testObj.getClass() == BautaV4.class);
  }

  @Test
  void testCreateIPv6NonDefaultMask() {
    BautaV6 testObj = factory.createIPv6(ipv6Mask1);
    assertTrue(testObj.getClass() == BautaV6.class);
  }
}