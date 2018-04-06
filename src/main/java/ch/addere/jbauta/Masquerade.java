/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Concrete masquerading class.
 */
public class Masquerade {

  private InetAddress v4Mask;
  private InetAddress v6Mask;

  /**
   * Initialise bitmask for masking addresses.
   *
   * <p>Precondition: ipv4Mask is a valid IP version 4 address and not equals null</p>
   * <p>Precondition: ipv6Mask is a valid IP version 6 address and not equals null</p>
   *
   * @param v4Mask A valid IP version 4 address as masking reference
   * @param v6Mask A valid IP version 6 address as masking reference
   */
  protected Masquerade(final InetAddress v4Mask, final InetAddress v6Mask) {
    this.v4Mask = v4Mask;
    this.v6Mask = v6Mask;
  }


  /**
   * Mask any given IP address.
   *
   * This method masks IP addresses based on the constructor initialised mask addresses.
   *
   * <p>Precondition: addressToMask is a valid IP address and not equals null</p>
   *
   * @param addressToMask IP address to mask
   * @return Masked IP address
   * @throws UnknownHostException If {@code addressToMask} is not a valid IP address
   */
  protected InetAddress maskAnyAddress(final InetAddress addressToMask)
      throws UnknownHostException {

    return v4v6Wrapper(addressToMask);
  }

  /**
   * Mask only public routeable IP addresses.
   *
   * This method masks IP addresses based on the constructor initialised mask addresses.
   *
   * <p>Precondition: addressToMask is a valid IP address and not equals null</p>
   *
   * @param addressToMask IP address to mask
   * @return Masked IP address
   * @throws UnknownHostException If {@code addressToMask} is not a valid IP address
   */
  protected InetAddress maskPublicRoutableIPAddressOnly(final InetAddress addressToMask)
      throws UnknownHostException {

    final boolean isPrivateAddress =
        addressToMask.isAnyLocalAddress() ||
            addressToMask.isLoopbackAddress() ||
            addressToMask.isLinkLocalAddress() ||
            addressToMask.isSiteLocalAddress();

    return isPrivateAddress ? addressToMask : v4v6Wrapper(addressToMask);
  }

  private InetAddress v4v6Wrapper(final InetAddress addressToMask) throws UnknownHostException {
    if (AddressUtil.isIPv4(addressToMask)) {
      return masqueradeIPAddress(addressToMask, v4Mask);
    } else if (AddressUtil.isIPv6(addressToMask)) {
      return masqueradeIPAddress(addressToMask, v6Mask);
    } else {
      throw new UnknownHostException();
    }
  }

  private InetAddress masqueradeIPAddress(final InetAddress addressToMask,
      final InetAddress maskingAddress)
      throws UnknownHostException {

    int length = AddressUtil.getAddressLength(addressToMask);

    byte[] inputAddress = addressToMask.getAddress();
    byte[] mask = maskingAddress.getAddress();
    byte[] outputAddress = new byte[length];

    for (int i = 0; i < length; i++) {
      outputAddress[i] = (byte) (inputAddress[i] & mask[i]);
    }

    return InetAddress.getByAddress(outputAddress);
  }

}
