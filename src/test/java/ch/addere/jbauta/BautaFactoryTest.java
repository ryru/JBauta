/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

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
  private static InetAddress ipv4Mask1;
  private static InetAddress ipv6Mask1;
  private InetAddress fullySetv4;
  private InetAddress fullySetv6;

  {
    try {
      fullySetv4 = InetAddress.getByName("255.255.255.255");
      fullySetv6 = InetAddress.getByName("FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  static {
    try {
      ipv4Mask1 = InetAddress.getByAddress(IPV4_MASK_1);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  static {
    try {
      ipv6Mask1 = InetAddress.getByAddress(IPV6_MASK_1);
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

  @Test
  void testIPv4SlashBitmask1() {
    BautaV4 bta = factory.createIPv4(0);
    try {
      bta.maskAny(fullySetv4);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4SlashBitmask2() {
    BautaV4 bta = factory.createIPv4(32);
    try {
      bta.maskAny(fullySetv4);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4SlashBitmask3() {
    Executable invalidIPSlashBitmask = () -> {
      BautaV4 bta = factory.createIPv4(-1);
      bta.maskAny(fullySetv4);
    };

    assertThrows(IllegalArgumentException.class, invalidIPSlashBitmask);
  }

  @Test
  void testIPv4SlashBitmask4() {
    Executable invalidIPSlashBitmask = () -> {
      BautaV4 bta = factory.createIPv4(33);
      bta.maskAny(fullySetv4);
    };

    assertThrows(IllegalArgumentException.class, invalidIPSlashBitmask);
  }

  @Test
  void testIPv4SlashBitmask5() {
    BautaV4 bta = factory.createIPv4(0);
    try {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.255");
      InetAddress outputShouldBe = InetAddress.getByName("0.0.0.0");
      assertEquals(outputShouldBe, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4SlashBitmask6() {
    BautaV4 bta = factory.createIPv4(8);
    try {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.255");
      InetAddress outputShouldBe = InetAddress.getByName("255.0.0.0");
      assertEquals(outputShouldBe, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4SlashBitmask7() {
    BautaV4 bta = factory.createIPv4(16);
    try {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.255");
      InetAddress outputShouldBe = InetAddress.getByName("255.255.0.0");
      assertEquals(outputShouldBe, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4SlashBitmask8() {
    BautaV4 bta = factory.createIPv4(24);
    try {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.255");
      InetAddress outputShouldBe = InetAddress.getByName("255.255.255.0");
      assertEquals(outputShouldBe, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4SlashBitmask9() {
    BautaV4 bta = factory.createIPv4(32);
    try {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.255");
      InetAddress outputShouldBe = InetAddress.getByName("255.255.255.255");
      assertEquals(outputShouldBe, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4SlashBitmask10() {
    BautaV4 bta = factory.createIPv4(9);
    try {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.255");
      InetAddress outputShouldBe = InetAddress.getByName("255.128.0.0");
      assertEquals(outputShouldBe, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4SlashBitmask11() {
    BautaV4 bta = factory.createIPv4(10);
    try {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.255");
      InetAddress outputShouldBe = InetAddress.getByName("255.192.0.0");
      assertEquals(outputShouldBe, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4SlashBitmask12() {
    BautaV4 bta = factory.createIPv4(11);
    try {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.255");
      InetAddress outputShouldBe = InetAddress.getByName("255.224.0.0");
      assertEquals(outputShouldBe, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4SlashBitmask13() {
    BautaV4 bta = factory.createIPv4(12);
    try {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.255");
      InetAddress outputShouldBe = InetAddress.getByName("255.240.0.0");
      assertEquals(outputShouldBe, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4SlashBitmask14() {
    BautaV4 bta = factory.createIPv4(13);
    try {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.255");
      InetAddress outputShouldBe = InetAddress.getByName("255.248.0.0");
      assertEquals(outputShouldBe, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4SlashBitmask15() {
    BautaV4 bta = factory.createIPv4(14);
    try {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.255");
      InetAddress outputShouldBe = InetAddress.getByName("255.252.0.0");
      assertEquals(outputShouldBe, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4SlashBitmask16() {
    BautaV4 bta = factory.createIPv4(15);
    try {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.255");
      InetAddress outputShouldBe = InetAddress.getByName("255.254.0.0");
      assertEquals(outputShouldBe, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6SlashBitmask1() {
    BautaV6 bta = factory.createIPv6(0);
    try {
      bta.maskAny(fullySetv6);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6SlashBitmask2() {
    BautaV6 bta = factory.createIPv6(32);
    try {
      bta.maskAny(fullySetv6);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6SlashBitmask3() {
    Executable invalidIPSlashBitmask = () -> {
      BautaV6 bta = factory.createIPv6(-1);
      bta.maskAny(fullySetv6);
    };

    assertThrows(IllegalArgumentException.class, invalidIPSlashBitmask);
  }

  @Test
  void testIPv6SlashBitmask4() {
    Executable invalidIPSlashBitmask = () -> {
      BautaV6 bta = factory.createIPv6(129);
      bta.maskAny(fullySetv6);
    };

    assertThrows(IllegalArgumentException.class, invalidIPSlashBitmask);
  }

  @Test
  void testIPv6SlashBitmask5() {
    BautaV6 bta = factory.createIPv6(0);
    try {
      InetAddress ipv6 = fullySetv6;
      InetAddress outputShouldBe = InetAddress.getByName("::");
      assertEquals(outputShouldBe, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6SlashBitmask6() {
    BautaV6 bta = factory.createIPv6(16);
    try {
      InetAddress ipv6 = fullySetv6;
      InetAddress outputShouldBe = InetAddress.getByName("FFFF::");
      assertEquals(outputShouldBe, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6SlashBitmask7() {
    BautaV6 bta = factory.createIPv6(32);
    try {
      InetAddress ipv6 = fullySetv6;
      InetAddress outputShouldBe = InetAddress.getByName("FFFF:FFFF::");
      assertEquals(outputShouldBe, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6SlashBitmask8() {
    BautaV6 bta = factory.createIPv6(48);
    try {
      InetAddress ipv6 = fullySetv6;
      InetAddress outputShouldBe = InetAddress.getByName("FFFF:FFFF:FFFF::");
      assertEquals(outputShouldBe, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6SlashBitmask9() {
    BautaV6 bta = factory.createIPv6(128);
    try {
      InetAddress ipv6 = fullySetv6;
      InetAddress outputShouldBe = fullySetv6;
      assertEquals(outputShouldBe, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6SlashBitmask10() {
    BautaV6 bta = factory.createIPv6(17);
    try {
      InetAddress ipv6 = fullySetv6;
      InetAddress outputShouldBe = InetAddress.getByName("FFFF:8000::");
      assertEquals(outputShouldBe, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6SlashBitmask11() {
    BautaV6 bta = factory.createIPv6(18);
    try {
      InetAddress ipv6 = fullySetv6;
      InetAddress outputShouldBe = InetAddress.getByName("FFFF:C000::");
      assertEquals(outputShouldBe, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6SlashBitmask12() {
    BautaV6 bta = factory.createIPv6(19);
    try {
      InetAddress ipv6 = fullySetv6;
      InetAddress outputShouldBe = InetAddress.getByName("FFFF:E000::");
      assertEquals(outputShouldBe, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6SlashBitmask13() {
    BautaV6 bta = factory.createIPv6(20);
    try {
      InetAddress ipv6 = fullySetv6;
      InetAddress outputShouldBe = InetAddress.getByName("FFFF:F000::");
      assertEquals(outputShouldBe, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6SlashBitmask14() {
    BautaV6 bta = factory.createIPv6(21);
    try {
      InetAddress ipv6 = fullySetv6;
      InetAddress outputShouldBe = InetAddress.getByName("FFFF:F800::");
      assertEquals(outputShouldBe, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6SlashBitmask15() {
    BautaV6 bta = factory.createIPv6(22);
    try {
      InetAddress ipv6 = fullySetv6;
      InetAddress outputShouldBe = InetAddress.getByName("FFFF:FC00::");
      assertEquals(outputShouldBe, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6SlashBitmask16() {
    BautaV6 bta = factory.createIPv6(23);
    try {
      InetAddress ipv6 = fullySetv6;
      InetAddress outputShouldBe = InetAddress.getByName("FFFF:FE00::");
      assertEquals(outputShouldBe, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }
}