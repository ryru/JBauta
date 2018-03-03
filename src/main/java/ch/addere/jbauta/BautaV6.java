/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import java.net.Inet6Address;
import java.net.UnknownHostException;

/**
 * Bauta IPv6 context.
 *
 * @author Pascal K. (ryru@addere.ch)
 * @version $Id$
 */
public class BautaV6 extends Bauta {

  /**
   * Create BautaV6 instance.
   *
   * @param mask IPv6 address to masquerade
   */
  public BautaV6(final Inet6Address mask) {
    super(mask);
  }

  /**
   * Masquerade any IPv6 address.
   *
   * This method masquerades any given IPv6 address with the bitmask set in this instance.
   *
   * @param addressToMask IPv6 address to mask
   * @return Anonymised IPv6 instance
   * @throws UnknownHostException If provided IPv6 is not valid
   */
  public Inet6Address maskAny(final Inet6Address addressToMask) throws UnknownHostException {
    if (addressToMask == null) {
      throw new IllegalArgumentException("NULL reference: addressToMask");
    }

    return (Inet6Address) maskAnyIPAddress(addressToMask);
  }

  /**
   * Masquerade public routable IPv6 address only.
   *
   * This method masquerades public routable IPv6 address only with the bitmask set in this instance. All private IP addresses will not be anonymised (see RFC4291).
   *
   * @param addressToMask IPv6 address to mask
   * @return Anonymised IPv6 instance
   * @throws UnknownHostException If provided IPv6 is not valid
   */
  public Inet6Address maskPublicRoutableOnly(final Inet6Address addressToMask)
      throws UnknownHostException {
    if (addressToMask == null) {
      throw new IllegalArgumentException("NULL reference: addressToMask");
    }

    return (Inet6Address) maskPublicRoutableIPAddressOnly(addressToMask);
  }

}
