package share

import (
	"github.com/TypingHare/burrow/v2026/burrow/redirector/share"
	"github.com/TypingHare/burrow/v2026/kernel"
	"github.com/spf13/cobra"
)

func GetWebRedirectionHandler(
	d kernel.DecorationInstance,
	silent bool,
) share.RedirectionHandler {
	return share.GetSimpleRedirectionHandler(
		func(cmd *cobra.Command, name string) error {
			return OpenWeb(d, name)
		},
	)
}
