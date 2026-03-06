package command

import (
	"github.com/TypingHare/web.carton/v2026/web/web/share"
	"github.com/spf13/cobra"
)

func OpenCommand(d share.WebDecorationLike) *cobra.Command {
	command := &cobra.Command{
		Use:   "open <name>",
		Short: "Open a web",
		Args:  cobra.ExactArgs(2),
		RunE: func(cmd *cobra.Command, args []string) error {
			return share.OpenWeb(d, args[0])
		},
	}

	return command
}
