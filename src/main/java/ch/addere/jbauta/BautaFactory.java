/*
 * Copyright 2017 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;

/**
 * Bauta Factory for creating IPv4 or IPv6 masquerading context.
 *
 */
public class BautaFactory {

  private static final byte[] IPV4_DEFAULT_MASK = {
      (byte) 0xFF, (byte) 0xFF, (byte) 0xF0, (byte) 0x00};
  private static final byte[] IPV6_DEFAULT_MASK = {
      (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

  /**
   * Create an IPv4 default bitmask context.
   *
   * An instance of BautaV4 with a default bitmask truncates the last 12 bit of a given address. Therefor the default IPv4 bitmask is set to 255.255.240.0 or 0xFFFFF000 in hex notation. This is the privacy by default method.
   *
   * @return Instance of BautaV4 with default bitmask
   */
  public BautaV4 createIPv4() {
    try {
      return new BautaV4((Inet4Address) Inet4Address.getByAddress(IPV4_DEFAULT_MASK));
    } catch (UnknownHostException e) {
      throw new AssertionError(
          "this should never happen: the IP address is supposed to be always valid", e);
    }
  }

  /**
   * Create an IPv6 default bitmask context.
   *
   * An instance of BautaV6 with a default bitmask truncates the last 96 bit of a given address. Therefor the default IPv6 bitmask is set to FFFF:FFFF:: in IPv6 short notation. This is the privacy by default method.
   *
   * @return Instance of BautaV6 with default bitmask
   */
  public BautaV6 createIPv6() {
    try {
      return new BautaV6((Inet6Address) Inet6Address.getByAddress(IPV6_DEFAULT_MASK));
    } catch (UnknownHostException e) {
      throw new AssertionError(
          "this should never happen: the IP address is supposed to be always valid", e);
    }
  }

  /**
   * Create an IPv4 context with a non-default bitmask context.
   *
   * By setting the appropriate bitmask this method allows to control every single bit of a given IPv4 to masquerade. This method allows to create own bitmask to masquerade IPv4 with.
   *
   * <p>Example 1: A bitmask address set to 255.255.255.255 will not modify any address while a bitmask address set to 0.0.0.0 will always return 0.0.0.0 regardless of the input IPv4 address.</p>
   *
   * <p>Example 2: A bitmask address set to 255.240.0.0 will only keep the first 12 bits of a given IPv4 address to mask.</p>
   *
   * @param maskingAddress Bitmask address to use for masquerading
   * @return Instance of BautaV4 with non-default bitmask
   */
  public BautaV4 createIPv4(Inet4Address maskingAddress) {
    if (maskingAddress == null) {
      throw new IllegalArgumentException("NULL reference: maskingAddress");
    }

    return new BautaV4(maskingAddress);
  }

  /**
   * Create an IPv6 context with a non-default bitmask context.
   *
   * By setting the appropriate bitmask this method allows to control every single bit of a given IPv6 to masquerade. This method allows to create own bitmask to masquerade IPv6 with.
   *
   * <p>Example 1: A bitmask address set to FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF will not modify any address while a bitmask address set to :: will always return :: regardless of the input IPv6 address.</p>
   *
   * <p>Example 2: A bitmask address set to FFF0:: will only keep the first 12 bits of a given IPv6 address to mask.</p>
   *
   * @param maskingAddress Bitmask address to use for masquerading
   * @return Instance of BautaV6 with non-default bitmask
   */
  public BautaV6 createIPv6(Inet6Address maskingAddress) {
    if (maskingAddress == null) {
      throw new IllegalArgumentException("NULL reference: maskingAddress");
    }

    return new BautaV6(maskingAddress);
  }

}
