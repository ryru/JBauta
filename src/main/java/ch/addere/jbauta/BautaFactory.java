/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * Bauta Factory for creating IP version 4 or 6 masquerading context.
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
   * Create an IP default bitmask context.
   *
   * An instance of Bauta with a default bitmask truncates the last 12 bit of a given IP version 4 address or the last 96 bit of a given IP version 6 address.
   *
   * @return Instance of Bauta with default bitmask
   */
  public Bauta createDefaultIPMask() {
    try {
      InetAddress defaultIPv4Bitmask = InetAddress.getByAddress(IPV4_DEFAULT_MASK);
      InetAddress defaultIPv6Bitmask = InetAddress.getByAddress(IPV6_DEFAULT_MASK);
      return new Bauta(defaultIPv4Bitmask, defaultIPv6Bitmask);
    } catch (UnknownHostException e) {
      throw new AssertionError(
          "this should never happen: the IP address is supposed to be always valid", e);
    }
  }

  /**
   * Create an IP custom bitmask context.
   *
   * An instance of Bauta with a custom set bitmask in CIDR notation.
   *
   * <p>Example 1: A bitmask address set to 32 will result in an address bitmask of 255.255.255.255 and therefor not modify any address while a bitmask address set to 0 will always return 0.0.0.0 regardless of the input IPv4 address.</p>
   * <p>Example 2: A bitmask address set to 12 will only keep the first 12 bits of a given IPv4 address to mask.</p>
   * <p>Example 3: A bitmask address set to 128 will result in an address bitmask of FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF and therefor not modify any address while a bitmask address set to 0 will always return :: regardless of the input IPv6 address.</p>
   * <p>Example 4: A bitmask address set to 12 will only keep the first 12 bits of a given IPv6 address to mask.</p>
   *
   * @param ipv4Mask Bitmask between 0 and 32 to use for masquerading
   * @param ipv6Mask Bitmask between 0 and 128 to use for masquerading
   * @return Instance of Bauta with custom set bitmask
   */
  public Bauta createCustomIPMask(int ipv4Mask, int ipv6Mask) {
    checkBitmaskParameter(ipv4Mask, ipv6Mask);

    InetAddress v4mask = AddressUtil.createIPv4FromBitmask(ipv4Mask);
    InetAddress v6Mask = AddressUtil.createIPv6FromBitmask(ipv6Mask);

    return new Bauta(v4mask, v6Mask);
  }

  /**
   * Create an IP custom address bitmask context.
   *
   * By setting the appropriate bitmask this method allows to control every single bit of a given IPv4 to masquerade. This method allows to create own bitmask to masquerade IPv4 with.
   *
   * <p>Example 1: A bitmask address set to 255.255.255.255 will not modify any address while a bitmask address set to 0.0.0.0 will always return 0.0.0.0 regardless of the input IPv4 address.</p>
   * <p>Example 2: A bitmask address set to 255.240.0.0 will only keep the first 12 bits of a given IPv4 address to mask.</p>
   * <p>Example 3: A bitmask address set to FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF will not modify any address while a bitmask address set to :: will always return :: regardless of the input IPv6 address.</p>
   * <p>Example 4: A bitmask address set to FFF0:: will only keep the first 12 bits of a given IPv6 address to mask.</p>
   *
   * @param ipv4Mask Bitmask version 4 address to use for masquerading
   * @param ipv6Mask Bitmask version 6 address to use for masquerading
   * @return Instance of Bauta with custom address bitmask
   */
  public Bauta createCustomIPMask(final InetAddress ipv4Mask, final InetAddress ipv6Mask) {
    final InetAddress v4mask = Objects.requireNonNull(ipv4Mask);
    final InetAddress v6mask = Objects.requireNonNull(ipv6Mask);

    checkAddressParameter(ipv4Mask, ipv6Mask);

    return new Bauta(v4mask, v6mask);
  }

  private void checkBitmaskParameter(int ipv4, int ipv6) {
    if (!AddressUtil.isValidIPv4CIDR(ipv4)) {
      throw new IllegalArgumentException("Invalid IPv4 bitmask. Has to be between 0 and 32");
    }

    if (!AddressUtil.isValidIPv6CIDR(ipv6)) {
      throw new IllegalArgumentException("Invalid IPv6 bitmask. Has to be between 0 and 128");
    }
  }

  private void checkAddressParameter(final InetAddress ipv4, final InetAddress ipv6) {
    if (!AddressUtil.isIPv4(ipv4)) {
      throw new IllegalArgumentException("Invalid IPv4 address.");
    }

    if (!AddressUtil.isIPv6(ipv6)) {
      throw new IllegalArgumentException("Invalid IPv6 address.");
    }
  }

}
