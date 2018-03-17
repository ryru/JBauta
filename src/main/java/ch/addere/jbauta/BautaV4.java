/*
 * Copyright 2017 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.jetbrains.annotations.NotNull;

/**
 * Bauta IPv4 context.
 *
 */
public class BautaV4 extends Bauta {

  private static final int IPV4_BYTE_COUNT = 4;

  /**
   * Create BautaV4 instance.
   *
   * @param mask IPv4 address to masquerade
   */
  public BautaV4(final InetAddress mask) {
    super(mask);

    if (!isIPv4(mask)) {
      throw new IllegalArgumentException("Error: mask is not a valid IP version 4 address");
    }
  }

  /**
   * Masquerade any IPv4 address.
   *
   * This method masquerades any given IPv4 address with the bitmask set in this instance.
   *
   * @param addressToMask IPv4 address to mask
   * @return Anonymised IPv4 instance
   * @throws UnknownHostException If provided IPv4 is not valid
   */
  public InetAddress maskAny(@NotNull final InetAddress addressToMask)
      throws UnknownHostException {

    if (isIPv4(addressToMask)) {
      return maskAnyIPAddress(addressToMask);
    } else {
      return addressToMask;
    }
  }

  /**
   * Masquerade public routable IPv4 address only.
   *
   * This method masquerades public routable IPv4 address only with the bitmask set in this instance. All private IP addresses will not be anonymised (see RFC1918).
   *
   * @param addressToMask IPv4 address to mask
   * @return Anonymised IPv4 instance
   * @throws UnknownHostException If provided IPv4 is not valid
   */
  public InetAddress maskPublicRoutableOnly(@NotNull final InetAddress addressToMask)
      throws UnknownHostException {

    if (isIPv4(addressToMask)) {
      return maskPublicRoutableIPAddressOnly(addressToMask);
    } else {
      return addressToMask;
    }
  }

  private boolean isIPv4(final InetAddress address) {
    boolean isIPv4 = true;
    int addressByteCount = address.getAddress().length;

    if (addressByteCount != IPV4_BYTE_COUNT) {
      isIPv4 = false;
    }

    return isIPv4;
  }

}
